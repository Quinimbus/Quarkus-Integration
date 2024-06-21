package cloud.quinimbus.integration.quarkus.persistence;

import cloud.quinimbus.config.api.ConfigNode;
import cloud.quinimbus.config.cdi.ConfigPath;
import cloud.quinimbus.persistence.api.PersistenceContext;
import cloud.quinimbus.persistence.api.entity.EntityWriterInitialisationException;
import cloud.quinimbus.persistence.api.lifecycle.EntityPostSaveEvent;
import io.quarkus.runtime.Startup;
import io.vertx.core.eventbus.EventBus;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@Startup
@ApplicationScoped
public class PersistenceEventBusIntegration {

    @Inject
    @ConfigPath(value = "persistence", optional = true)
    private ConfigNode configNode;

    @Inject
    private PersistenceContext persistenceContext;

    @Inject
    private EventBus eventBus;

    @PostConstruct
    public void initIntegration() {
        this.configNode.asNode("quarkus", "events", "schemas").ifPresent(eventsConfigNode -> {
            eventsConfigNode.stream().forEach(schemaConfigNode -> {
                var schemaId = schemaConfigNode.name();
                var types = schemaConfigNode.asStringList("types");
                types.forEach(typeId -> this.registerEventsForType(schemaId, typeId));
            });
        });
    }

    private void registerEventsForType(String schemaId, String typeId) {
        this.persistenceContext.onLifecycleEvent(
                schemaId, EntityPostSaveEvent.class, typeId, this::handlePostSaveEvent);
    }

    private void handlePostSaveEvent(EntityPostSaveEvent event) {
        try {
            var entityType = event.entity().getType();
            var recordType = this.persistenceContext.getRecordEntityRegistry().getRecordType(entityType.id());
            var entityWriter = this.persistenceContext.getRecordEntityWriter(entityType, recordType);
            var record = entityWriter.write(event.entity());
            this.eventBus.publish(
                    "quinimbus.entity.%s.post-save"
                            .formatted(event.entity().getType().id()),
                    new EntityRecordPostSaveEvent<>(record, event.mutatedProperties()));
        } catch (EntityWriterInitialisationException ex) {
            throw new IllegalStateException(ex);
        }
    }
}

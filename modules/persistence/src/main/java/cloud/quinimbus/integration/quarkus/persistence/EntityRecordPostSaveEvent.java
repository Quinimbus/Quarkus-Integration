package cloud.quinimbus.integration.quarkus.persistence;

import java.util.Set;

public record EntityRecordPostSaveEvent<T extends Record>(T entity, Set<String> mutatedProperties) {}

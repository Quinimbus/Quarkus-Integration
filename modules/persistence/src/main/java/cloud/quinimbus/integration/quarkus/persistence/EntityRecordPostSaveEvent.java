package cloud.quinimbus.integration.quarkus.persistence;

import java.util.List;

public record EntityRecordPostSaveEvent<T extends Record>(T entity, List<String> mutatedProperties) {}

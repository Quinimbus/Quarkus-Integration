package cloud.quinimbus.integration.quarkus.persistence;

public record EntityRecordPostLoadEvent<T extends Record>(T entity) {}

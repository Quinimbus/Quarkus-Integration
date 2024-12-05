package cloud.quinimbus.integration.quarkus.persistence;

import cloud.quinimbus.persistence.api.lifecycle.EntityDiffEvent;
import cloud.quinimbus.persistence.api.lifecycle.diff.Diff;
import java.util.Set;

public record EntityRecordPreSaveEvent<T extends Record>(T entity, Set<Diff<Object>> diffs)
        implements EntityDiffEvent {}

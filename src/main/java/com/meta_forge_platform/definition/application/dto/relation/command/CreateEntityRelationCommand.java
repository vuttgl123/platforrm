package com.meta_forge_platform.definition.application.dto.relation.command;

import java.util.Map;

public record CreateEntityRelationCommand(
        Long sourceEntityId,
        Long targetEntityId,
        String code,
        String name,
        String relationType,
        String relationKind,
        String mappedByField,
        String inverseField,
        Boolean active,
        Map<String, Object> config
) {
}

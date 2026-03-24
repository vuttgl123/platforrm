package com.meta_forge_platform.definition.application.dto.relation.response;

import java.time.LocalDateTime;
import java.util.Map;

public record EntityRelationResponse(
        Long id,
        Long sourceEntityId,
        String sourceEntityCode,
        Long targetEntityId,
        String targetEntityCode,
        String code,
        String name,
        String relationType,
        String relationKind,
        String mappedByField,
        String inverseField,
        Boolean active,
        Map<String, Object> config,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
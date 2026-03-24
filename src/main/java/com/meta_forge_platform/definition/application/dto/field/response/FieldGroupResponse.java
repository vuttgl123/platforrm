package com.meta_forge_platform.definition.application.dto.field.response;

import java.time.LocalDateTime;
import java.util.Map;

public record FieldGroupResponse(
        Long id,
        Long entityId,
        String entityCode,
        String code,
        String name,
        String description,
        Integer sortOrder,
        Map<String, Object> config,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

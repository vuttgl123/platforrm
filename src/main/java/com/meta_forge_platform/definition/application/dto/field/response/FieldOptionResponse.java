package com.meta_forge_platform.definition.application.dto.field.response;

import java.time.LocalDateTime;
import java.util.Map;

public record FieldOptionResponse(
        Long id,
        Long fieldId,
        String fieldCode,
        String code,
        String label,
        String value,
        Integer sortOrder,
        Boolean active,
        Boolean defaultOption,
        Map<String, Object> config,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

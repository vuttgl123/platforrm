package com.meta_forge_platform.definition.application.dto.field.response;

import java.time.LocalDateTime;
import java.util.Map;

public record FieldResponse(
        Long id,
        Long entityId,
        String entityCode,
        String code,
        String name,
        String dataType,
        String uiType,
        String storageType,
        Boolean required,
        Boolean uniqueField,
        Boolean searchable,
        Boolean filterable,
        Boolean sortable,
        Boolean listable,
        Boolean detailVisible,
        Boolean editable,
        Boolean system,
        Integer sortOrder,
        Long relationEntityId,
        String relationEntityCode,
        String relationType,
        Map<String, Object> defaultValue,
        Map<String, Object> validation,
        Map<String, Object> config,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

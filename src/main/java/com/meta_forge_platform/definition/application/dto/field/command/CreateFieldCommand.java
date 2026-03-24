package com.meta_forge_platform.definition.application.dto.field.command;

import java.util.Map;

public record CreateFieldCommand(
        Long entityId,
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
        String relationType,
        Map<String, Object> defaultValue,
        Map<String, Object> validation,
        Map<String, Object> config
) {
}

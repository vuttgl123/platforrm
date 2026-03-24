package com.meta_forge_platform.definition.application.dto.field.command;

import java.util.Map;

public record CreateFieldOptionCommand(
        Long fieldId,
        String code,
        String label,
        String value,
        Integer sortOrder,
        Boolean active,
        Boolean defaultOption,
        Map<String, Object> config
) {
}

package com.meta_forge_platform.definition.application.dto.field.command;

import java.util.Map;

public record CreateFieldGroupCommand(
        Long entityId,
        String code,
        String name,
        String description,
        Integer sortOrder,
        Map<String, Object> config
) {
}

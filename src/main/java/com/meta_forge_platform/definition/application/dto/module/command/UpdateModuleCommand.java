package com.meta_forge_platform.definition.application.dto.module.command;

public record UpdateModuleCommand(
        Long moduleId,
        String name,
        String description,
        Integer sortOrder,
        Boolean system
) {
}

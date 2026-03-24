package com.meta_forge_platform.definition.application.dto.module.command;

public record CreateModuleCommand(
        String code,
        String name,
        String description
) {
}

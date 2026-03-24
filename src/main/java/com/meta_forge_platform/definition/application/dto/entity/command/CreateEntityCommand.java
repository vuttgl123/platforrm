package com.meta_forge_platform.definition.application.dto.entity.command;

import java.util.Map;

public record CreateEntityCommand(
        Long moduleId,
        String code,
        String name,
        String tableStrategy,
        String description,
        Boolean root,
        Boolean active,
        String displayNamePattern,
        Map<String, Object> defaultSort,
        Map<String, Object> config
) {
}

package com.meta_forge_platform.definition.application.dto.entity.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;

public record CreateEntityRequest(

        @NotNull(message = "Module id must not be null")
        Long moduleId,

        @NotBlank(message = "Entity code must not be blank")
        @Size(max = 100, message = "Entity code must be <= 100 characters")
        String code,

        @NotBlank(message = "Entity name must not be blank")
        @Size(max = 255, message = "Entity name must be <= 255 characters")
        String name,

        @Size(max = 30, message = "Table strategy must be <= 30 characters")
        String tableStrategy,

        String description,

        Boolean root,

        Boolean active,

        @Size(max = 500, message = "Display name pattern must be <= 500 characters")
        String displayNamePattern,

        Map<String, Object> defaultSort,

        Map<String, Object> config
) {
}

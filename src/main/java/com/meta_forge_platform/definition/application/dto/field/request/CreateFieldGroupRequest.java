package com.meta_forge_platform.definition.application.dto.field.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;

public record CreateFieldGroupRequest(

        @NotNull(message = "Entity id must not be null")
        Long entityId,

        @NotBlank(message = "Group code must not be blank")
        @Size(max = 100, message = "Group code must be <= 100 characters")
        String code,

        @NotBlank(message = "Group name must not be blank")
        @Size(max = 255, message = "Group name must be <= 255 characters")
        String name,

        String description,

        Integer sortOrder,

        Map<String, Object> config
) {
}

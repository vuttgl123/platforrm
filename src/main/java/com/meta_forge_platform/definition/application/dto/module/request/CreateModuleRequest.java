package com.meta_forge_platform.definition.application.dto.module.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateModuleRequest(

        @NotBlank(message = "Module code must not be blank")
        @Size(max = 100, message = "Module code must be <= 100 characters")
        String code,

        @NotBlank(message = "Module name must not be blank")
        @Size(max = 255, message = "Module name must be <= 255 characters")
        String name,

        String description
) {
}

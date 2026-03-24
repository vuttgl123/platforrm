package com.meta_forge_platform.definition.application.dto.field.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;

public record CreateFieldOptionRequest(

        @NotNull(message = "Field id must not be null")
        Long fieldId,

        @NotBlank(message = "Option code must not be blank")
        @Size(max = 100, message = "Option code must be <= 100 characters")
        String code,

        @NotBlank(message = "Option label must not be blank")
        @Size(max = 255, message = "Option label must be <= 255 characters")
        String label,

        @NotBlank(message = "Option value must not be blank")
        @Size(max = 255, message = "Option value must be <= 255 characters")
        String value,

        Integer sortOrder,
        Boolean active,
        Boolean defaultOption,
        Map<String, Object> config
) {
}

package com.meta_forge_platform.definition.application.dto.field.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;

public record CreateFieldRequest(

        @NotNull(message = "Entity id must not be null")
        Long entityId,

        @NotBlank(message = "Field code must not be blank")
        @Size(max = 100, message = "Field code must be <= 100 characters")
        String code,

        @NotBlank(message = "Field name must not be blank")
        @Size(max = 255, message = "Field name must be <= 255 characters")
        String name,

        @NotBlank(message = "Data type must not be blank")
        @Size(max = 50, message = "Data type must be <= 50 characters")
        String dataType,

        @Size(max = 50, message = "UI type must be <= 50 characters")
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

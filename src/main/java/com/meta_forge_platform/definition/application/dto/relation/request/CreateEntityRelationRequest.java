package com.meta_forge_platform.definition.application.dto.relation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;

public record CreateEntityRelationRequest(

        @NotNull(message = "Source entity id must not be null")
        Long sourceEntityId,

        @NotNull(message = "Target entity id must not be null")
        Long targetEntityId,

        @NotBlank(message = "Relation code must not be blank")
        @Size(max = 100, message = "Relation code must be <= 100 characters")
        String code,

        @NotBlank(message = "Relation name must not be blank")
        @Size(max = 255, message = "Relation name must be <= 255 characters")
        String name,

        @NotBlank(message = "Relation type must not be blank")
        String relationType,

        String relationKind,
        String mappedByField,
        String inverseField,
        Boolean active,
        Map<String, Object> config
) {
}

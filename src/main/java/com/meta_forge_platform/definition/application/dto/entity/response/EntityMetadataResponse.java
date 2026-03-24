package com.meta_forge_platform.definition.application.dto.entity.response;

import com.meta_forge_platform.definition.application.dto.field.response.FieldGroupResponse;
import com.meta_forge_platform.definition.application.dto.field.response.FieldResponse;
import com.meta_forge_platform.definition.application.dto.relation.response.EntityRelationResponse;

import java.util.List;

public record EntityMetadataResponse(
        EntityResponse entity,
        List<FieldGroupResponse> fieldGroups,
        List<FieldResponse> fields,
        List<EntityRelationResponse> relations
) {
}

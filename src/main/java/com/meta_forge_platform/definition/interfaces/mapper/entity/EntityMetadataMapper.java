package com.meta_forge_platform.definition.interfaces.mapper.entity;

import com.meta_forge_platform.definition.application.dto.entity.response.EntityMetadataResponse;
import com.meta_forge_platform.definition.application.dto.entity.response.EntityResponse;
import com.meta_forge_platform.definition.application.dto.field.response.FieldGroupResponse;
import com.meta_forge_platform.definition.application.dto.field.response.FieldResponse;
import com.meta_forge_platform.definition.application.dto.relation.response.EntityRelationResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntityMetadataMapper {

    public EntityMetadataResponse toResponse(
            EntityResponse entity,
            List<FieldGroupResponse> fieldGroups,
            List<FieldResponse> fields,
            List<EntityRelationResponse> relations
    ) {
        return new EntityMetadataResponse(entity, fieldGroups, fields, relations);
    }
}

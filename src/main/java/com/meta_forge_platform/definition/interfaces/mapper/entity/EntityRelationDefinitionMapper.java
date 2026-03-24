package com.meta_forge_platform.definition.interfaces.mapper.entity;

import com.meta_forge_platform.definition.application.dto.relation.command.CreateEntityRelationCommand;
import com.meta_forge_platform.definition.application.dto.relation.request.CreateEntityRelationRequest;
import com.meta_forge_platform.definition.application.dto.relation.response.EntityRelationResponse;
import com.meta_forge_platform.definition.domain.model.entity.EntityRelationDefinition;
import com.meta_forge_platform.definition.interfaces.mapper.DefinitionMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = DefinitionMapperConfig.class,
        uses = EntityRelationDefinitionMapperHelper.class
)
public interface EntityRelationDefinitionMapper {

    CreateEntityRelationCommand toCreateCommand(CreateEntityRelationRequest request);

    @Mapping(target = "sourceEntityId", source = "sourceEntity.id")
    @Mapping(target = "sourceEntityCode", source = "sourceEntity.code")
    @Mapping(target = "targetEntityId", source = "targetEntity.id")
    @Mapping(target = "targetEntityCode", source = "targetEntity.code")
    @Mapping(target = "relationType", source = "relationType")
    @Mapping(target = "relationKind", source = "relationKind")
    EntityRelationResponse toResponse(EntityRelationDefinition relation);
}

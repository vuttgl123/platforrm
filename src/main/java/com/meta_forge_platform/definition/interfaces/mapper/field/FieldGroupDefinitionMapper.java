package com.meta_forge_platform.definition.interfaces.mapper.field;

import com.meta_forge_platform.definition.application.dto.field.command.CreateFieldGroupCommand;
import com.meta_forge_platform.definition.application.dto.field.request.CreateFieldGroupRequest;
import com.meta_forge_platform.definition.application.dto.field.response.FieldGroupResponse;
import com.meta_forge_platform.definition.domain.model.field.FieldGroupDefinition;
import com.meta_forge_platform.definition.interfaces.mapper.DefinitionMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = DefinitionMapperConfig.class)
public interface FieldGroupDefinitionMapper {

    CreateFieldGroupCommand toCreateCommand(CreateFieldGroupRequest request);

    @Mapping(target = "entityId", source = "entity.id")
    @Mapping(target = "entityCode", source = "entity.code")
    FieldGroupResponse toResponse(FieldGroupDefinition group);
}

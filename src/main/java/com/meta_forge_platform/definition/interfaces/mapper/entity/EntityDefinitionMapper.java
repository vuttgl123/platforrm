package com.meta_forge_platform.definition.interfaces.mapper.entity;

import com.meta_forge_platform.definition.application.dto.entity.command.CreateEntityCommand;
import com.meta_forge_platform.definition.application.dto.entity.command.UpdateEntityCommand;
import com.meta_forge_platform.definition.application.dto.entity.request.CreateEntityRequest;
import com.meta_forge_platform.definition.application.dto.entity.request.UpdateEntityRequest;
import com.meta_forge_platform.definition.application.dto.entity.response.EntityResponse;
import com.meta_forge_platform.definition.domain.model.entity.EntityDefinition;
import com.meta_forge_platform.definition.interfaces.mapper.DefinitionMapperConfig;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = DefinitionMapperConfig.class)
public interface EntityDefinitionMapper {

    CreateEntityCommand toCreateCommand(CreateEntityRequest request);

    @Mapping(target = "entityId", source = "entityId")
    UpdateEntityCommand toUpdateCommand(Long entityId, UpdateEntityRequest request);

    @Mapping(target = "moduleId", source = "module.id")
    @Mapping(target = "moduleCode", source = "module.code")
    EntityResponse toResponse(EntityDefinition entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "command.name")
    @Mapping(target = "tableStrategy", source = "command.tableStrategy")
    @Mapping(target = "description", source = "command.description")
    @Mapping(target = "root", source = "command.root")
    @Mapping(target = "active", source = "command.active")
    @Mapping(target = "displayNamePattern", source = "command.displayNamePattern")
    @Mapping(target = "defaultSort", source = "command.defaultSort")
    @Mapping(target = "config", source = "command.config")
    void updateEntity(@MappingTarget EntityDefinition entity, UpdateEntityCommand command);
}

package com.meta_forge_platform.definition.interfaces.mapper.module;

import com.meta_forge_platform.definition.application.dto.module.command.CreateModuleCommand;
import com.meta_forge_platform.definition.application.dto.module.command.UpdateModuleCommand;
import com.meta_forge_platform.definition.application.dto.module.request.CreateModuleRequest;
import com.meta_forge_platform.definition.application.dto.module.request.UpdateModuleRequest;
import com.meta_forge_platform.definition.application.dto.module.response.ModuleResponse;
import com.meta_forge_platform.definition.domain.model.module.ModuleDefinition;
import com.meta_forge_platform.definition.interfaces.mapper.DefinitionMapperConfig;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = DefinitionMapperConfig.class)
public interface ModuleDefinitionMapper {

    CreateModuleCommand toCreateCommand(CreateModuleRequest request);

    @Mapping(target = "moduleId", source = "moduleId")
    UpdateModuleCommand toUpdateCommand(Long moduleId, UpdateModuleRequest request);

    ModuleResponse toResponse(ModuleDefinition module);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "command.name")
    @Mapping(target = "description", source = "command.description")
    @Mapping(target = "sortOrder", source = "command.sortOrder")
    @Mapping(target = "system", source = "command.system")
    void updateEntity(@MappingTarget ModuleDefinition module, UpdateModuleCommand command);
}

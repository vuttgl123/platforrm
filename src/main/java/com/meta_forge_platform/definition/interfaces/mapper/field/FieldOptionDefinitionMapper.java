package com.meta_forge_platform.definition.interfaces.mapper.field;

import com.meta_forge_platform.definition.application.dto.field.command.CreateFieldOptionCommand;
import com.meta_forge_platform.definition.application.dto.field.request.CreateFieldOptionRequest;
import com.meta_forge_platform.definition.application.dto.field.response.FieldOptionResponse;
import com.meta_forge_platform.definition.domain.model.field.FieldOptionDefinition;
import com.meta_forge_platform.definition.interfaces.mapper.DefinitionMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = DefinitionMapperConfig.class)
public interface FieldOptionDefinitionMapper {

    CreateFieldOptionCommand toCreateCommand(CreateFieldOptionRequest request);

    @Mapping(target = "fieldId", source = "field.id")
    @Mapping(target = "fieldCode", source = "field.code")
    FieldOptionResponse toResponse(FieldOptionDefinition option);
}

package com.meta_forge_platform.definition.interfaces.mapper.field;

import com.meta_forge_platform.definition.application.dto.field.command.CreateFieldCommand;
import com.meta_forge_platform.definition.application.dto.field.command.UpdateFieldCommand;
import com.meta_forge_platform.definition.application.dto.field.request.CreateFieldRequest;
import com.meta_forge_platform.definition.application.dto.field.request.UpdateFieldRequest;
import com.meta_forge_platform.definition.application.dto.field.response.FieldResponse;
import com.meta_forge_platform.definition.domain.model.field.FieldDefinition;
import com.meta_forge_platform.definition.interfaces.mapper.DefinitionMapperConfig;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        config = DefinitionMapperConfig.class,
        uses = FieldDefinitionMapperHelper.class
)
public interface FieldDefinitionMapper {

    CreateFieldCommand toCreateCommand(CreateFieldRequest request);

    @Mapping(target = "fieldId", source = "fieldId")
    UpdateFieldCommand toUpdateCommand(Long fieldId, UpdateFieldRequest request);

    @Mapping(target = "entityId", source = "entity.id")
    @Mapping(target = "entityCode", source = "entity.code")
    @Mapping(target = "storageType", source = "storageType")
    @Mapping(target = "relationEntityId", source = "relationEntity.id")
    @Mapping(target = "relationEntityCode", source = "relationEntity.code")
    @Mapping(target = "relationType", source = "relationType")
    FieldResponse toResponse(FieldDefinition field);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "command.name")
    @Mapping(target = "dataType", source = "command.dataType")
    @Mapping(target = "uiType", source = "command.uiType")
    @Mapping(target = "storageType", source = "command.storageType")
    @Mapping(target = "required", source = "command.required")
    @Mapping(target = "uniqueField", source = "command.uniqueField")
    @Mapping(target = "searchable", source = "command.searchable")
    @Mapping(target = "filterable", source = "command.filterable")
    @Mapping(target = "sortable", source = "command.sortable")
    @Mapping(target = "listable", source = "command.listable")
    @Mapping(target = "detailVisible", source = "command.detailVisible")
    @Mapping(target = "editable", source = "command.editable")
    @Mapping(target = "system", source = "command.system")
    @Mapping(target = "sortOrder", source = "command.sortOrder")
    @Mapping(target = "relationType", source = "command.relationType")
    @Mapping(target = "defaultValue", source = "command.defaultValue")
    @Mapping(target = "validation", source = "command.validation")
    @Mapping(target = "config", source = "command.config")
    void updateEntity(@MappingTarget FieldDefinition field, UpdateFieldCommand command);
}

package com.meta_forge_platform.definition.application.usecase.field;

import com.meta_forge_platform.definition.application.dto.field.command.CreateFieldCommand;
import com.meta_forge_platform.definition.application.dto.field.response.FieldResponse;
import com.meta_forge_platform.definition.domain.model.entity.EntityDefinition;
import com.meta_forge_platform.definition.domain.model.field.FieldDefinition;
import com.meta_forge_platform.definition.infrastructure.persistence.entity.EntityDefinitionRepository;
import com.meta_forge_platform.definition.infrastructure.persistence.field.FieldDefinitionRepository;
import com.meta_forge_platform.definition.interfaces.mapper.field.FieldDefinitionMapper;
import com.meta_forge_platform.definition.interfaces.mapper.field.FieldDefinitionMapperHelper;
import com.meta_forge_platform.shared.domain.exception.ConflictException;
import com.meta_forge_platform.shared.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateFieldUseCase {

    private final EntityDefinitionRepository entityRepository;
    private final FieldDefinitionRepository fieldRepository;
    private final FieldDefinitionMapper fieldMapper;
    private final FieldDefinitionMapperHelper fieldMapperHelper;

    @Transactional
    public FieldResponse execute(CreateFieldCommand command) {
        EntityDefinition entity = entityRepository.findById(command.entityId())
                .orElseThrow(() -> new NotFoundException("Entity not found: " + command.entityId()));

        if (fieldRepository.existsByEntity_IdAndCode(entity.getId(), command.code())) {
            throw new ConflictException("Field code already exists in entity: " + command.code());
        }

        var field = new FieldDefinition(
                entity,
                command.code(),
                command.name(),
                command.dataType()
        );

        field.changeUiType(command.uiType());

        if (command.storageType() != null) {
            field.changeStorageType(fieldMapperHelper.mapStorageType(command.storageType()));
        }

        if (command.required() != null) {
            field.setRequired(command.required());
        }

        if (command.uniqueField() != null) {
            field.setUniqueField(command.uniqueField());
        }

        if (command.searchable() != null) {
            field.setSearchable(command.searchable());
        }

        if (command.filterable() != null) {
            field.setFilterable(command.filterable());
        }

        if (command.sortable() != null) {
            field.setSortable(command.sortable());
        }

        if (command.listable() != null) {
            field.setListable(command.listable());
        }

        if (command.detailVisible() != null) {
            field.setDetailVisible(command.detailVisible());
        }

        if (command.editable() != null) {
            field.setEditable(command.editable());
        }

        if (command.system() != null) {
            field.setSystem(command.system());
        }

        if (command.sortOrder() != null) {
            field.changeSortOrder(command.sortOrder());
        }

        field.changeDefaultValue(command.defaultValue());
        field.changeValidation(command.validation());
        field.changeConfig(command.config());

        if (command.relationEntityId() != null) {
            EntityDefinition relationEntity = entityRepository.findById(command.relationEntityId())
                    .orElseThrow(() -> new NotFoundException("Relation entity not found: " + command.relationEntityId()));

            var relationType = fieldMapperHelper.mapRelationType(command.relationType());
            field.changeRelation(relationEntity, relationType);
        }

        var saved = fieldRepository.save(field);
        return fieldMapper.toResponse(saved);
    }
}

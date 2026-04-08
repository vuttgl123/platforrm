package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.field.*;
import com.meta_forge_platform.platform.application.mapper.DpFieldMapper;
import com.meta_forge_platform.platform.application.service.DpFieldService;
import com.meta_forge_platform.platform.domain.entity.DpEntity;
import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.platform.domain.entity.DpFieldGroup;
import com.meta_forge_platform.platform.domain.enumeration.FieldDataType;
import com.meta_forge_platform.platform.domain.enumeration.FieldStorageType;
import com.meta_forge_platform.platform.domain.enumeration.FieldUiType;
import com.meta_forge_platform.platform.domain.enumeration.RelationType;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpFieldGroupRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpFieldRepository;
import com.meta_forge_platform.shared.application.BaseServiceImpl;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
public class DpFieldServiceImpl
        extends BaseServiceImpl<DpField, DpFieldDto, CreateDpFieldCmd, UpdateDpFieldCmd, Long>
        implements DpFieldService {

    private final DpFieldRepository fieldRepository;
    private final DpEntityRepository entityRepository;
    private final DpFieldGroupRepository fieldGroupRepository;
    private final DpFieldMapper mapper;

    public DpFieldServiceImpl(DpFieldRepository fieldRepository,
                              DpEntityRepository entityRepository,
                              DpFieldGroupRepository fieldGroupRepository,
                              DpFieldMapper mapper) {
        super(fieldRepository);
        this.fieldRepository = fieldRepository;
        this.entityRepository = entityRepository;
        this.fieldGroupRepository = fieldGroupRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getEntityName() {
        return "DpField";
    }

    @Override
    protected DpFieldDto toDto(DpField entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected DpField toEntity(CreateDpFieldCmd command) {
        DpEntity entity = entityRepository.findActiveById(command.getEntityId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpEntity",
                        command.getEntityId()
                ));

        DpField field = DpField.create(
                entity,
                command.getFieldCode(),
                command.getFieldName(),
                toDataType(command.getDataType())
        );

        field.applyMetadata(
                resolveFieldGroup(command.getFieldGroupId()),
                command.getFieldName(),
                toUiType(command.getUiType()),
                toStorageType(command.getStorageType()),
                command.getIsRequired(),
                command.getIsUniqueField(),
                command.getIsSearchable(),
                command.getIsFilterable(),
                command.getIsSortable(),
                command.getIsListable(),
                command.getIsDetailVisible(),
                command.getIsEditable(),
                command.getSortOrder(),
                command.getDefaultValueJson(),
                command.getValidationJson(),
                command.getConfigJson(),
                resolveRelationEntity(command.getRelationEntityId()),
                toRelationType(command.getRelationType())
        );

        return field;
    }

    @Override
    protected void updateEntity(DpField entity, UpdateDpFieldCmd command) {
        entity.applyMetadata(
                resolveFieldGroup(command.getFieldGroupId()),
                command.getFieldName(),
                toUiType(command.getUiType()),
                toStorageType(command.getStorageType()),
                command.getIsRequired(),
                command.getIsUniqueField(),
                command.getIsSearchable(),
                command.getIsFilterable(),
                command.getIsSortable(),
                command.getIsListable(),
                command.getIsDetailVisible(),
                command.getIsEditable(),
                command.getSortOrder(),
                command.getDefaultValueJson(),
                command.getValidationJson(),
                command.getConfigJson(),
                resolveRelationEntity(command.getRelationEntityId()),
                toRelationType(command.getRelationType())
        );
    }

    @Override
    protected void validateCreateCommand(CreateDpFieldCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getEntityId(), "entityId");
        validateNotNull(command.getFieldCode(), "fieldCode");
        validateNotNull(command.getFieldName(), "fieldName");
        validateNotNull(command.getDataType(), "dataType");
        validateNotNull(command.getStorageType(), "storageType");
        validateNotNull(command.getIsRequired(), "isRequired");
        validateNotNull(command.getIsUniqueField(), "isUniqueField");
        validateNotNull(command.getIsSearchable(), "isSearchable");
        validateNotNull(command.getIsFilterable(), "isFilterable");
        validateNotNull(command.getIsSortable(), "isSortable");
        validateNotNull(command.getIsListable(), "isListable");
        validateNotNull(command.getIsDetailVisible(), "isDetailVisible");
        validateNotNull(command.getIsEditable(), "isEditable");
        validateNotNull(command.getSortOrder(), "sortOrder");
    }

    @Override
    protected void validateUpdateCommand(UpdateDpFieldCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getFieldName(), "fieldName");
        validateNotNull(command.getStorageType(), "storageType");
        validateNotNull(command.getIsRequired(), "isRequired");
        validateNotNull(command.getIsUniqueField(), "isUniqueField");
        validateNotNull(command.getIsSearchable(), "isSearchable");
        validateNotNull(command.getIsFilterable(), "isFilterable");
        validateNotNull(command.getIsSortable(), "isSortable");
        validateNotNull(command.getIsListable(), "isListable");
        validateNotNull(command.getIsDetailVisible(), "isDetailVisible");
        validateNotNull(command.getIsEditable(), "isEditable");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateDpFieldCmd command) {
        if (fieldRepository.existsByEntity_IdAndFieldCodeAndIsDeletedFalse(
                command.getEntityId(),
                command.getFieldCode()
        )) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "DpField",
                    command.getFieldCode()
            );
        }
    }

    @Override
    protected void beforeUpdate(DpField entity, UpdateDpFieldCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "DpField",
                    entity.getId()
            );
        }
    }

    @Override
    protected void beforeDelete(DpField entity) {
        if (Boolean.TRUE.equals(entity.getIsSystem())) {
            throw AppException.of(
                    ErrorCode.BAD_REQUEST,
                    "systemField",
                    entity.getFieldCode()
            );
        }
    }

    @Override
    protected Specification<DpField> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("fieldCode")), p),
                cb.like(cb.lower(root.get("fieldName")), p)
        );
    }

    @Override
    public List<DpFieldSummaryDto> findAllByEntity(Long entityId) {
        return mapper.toSummaryDtoList(
                fieldRepository.findAllByEntity_IdAndIsDeletedFalseOrderBySortOrderAsc(entityId)
        );
    }

    @Override
    public List<DpFieldSummaryDto> findAllByFieldGroup(Long groupId) {
        return mapper.toSummaryDtoList(
                fieldRepository.findAllByFieldGroup_IdAndIsDeletedFalseOrderBySortOrderAsc(groupId)
        );
    }

    @Override
    public List<DpFieldSummaryDto> findSearchableFields(Long entityId) {
        return mapper.toSummaryDtoList(
                fieldRepository.findSearchableFields(entityId)
        );
    }

    private DpFieldGroup resolveFieldGroup(Long id) {
        if (id == null) {
            return null;
        }
        return fieldGroupRepository.findActiveById(id)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpFieldGroup",
                        id
                ));
    }

    private DpEntity resolveRelationEntity(Long id) {
        if (id == null) {
            return null;
        }
        return entityRepository.findActiveById(id)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpEntity",
                        id
                ));
    }

    private FieldDataType toDataType(String raw) {
        try {
            return FieldDataType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "dataType", raw);
        }
    }

    private FieldUiType toUiType(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return FieldUiType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "uiType", raw);
        }
    }

    private FieldStorageType toStorageType(String raw) {
        try {
            return FieldStorageType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "storageType", raw);
        }
    }

    private RelationType toRelationType(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return RelationType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "relationType", raw);
        }
    }
}
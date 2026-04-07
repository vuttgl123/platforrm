package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.entityconstraint.*;
import com.meta_forge_platform.platform.application.mapper.DpEntityConstraintMapper;
import com.meta_forge_platform.platform.application.service.DpEntityConstraintService;
import com.meta_forge_platform.platform.domain.entity.DpEntity;
import com.meta_forge_platform.platform.domain.entity.DpEntityConstraint;
import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.platform.domain.enumeration.ConstraintType;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityConstraintRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRepository;
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
public class DpEntityConstraintServiceImpl
        extends BaseServiceImpl<DpEntityConstraint, DpEntityConstraintDto, CreateDpEntityConstraintCmd, UpdateDpEntityConstraintCmd, Long>
        implements DpEntityConstraintService {

    private final DpEntityConstraintRepository constraintRepository;
    private final DpEntityRepository entityRepository;
    private final DpFieldRepository fieldRepository;
    private final DpEntityConstraintMapper mapper;

    public DpEntityConstraintServiceImpl(DpEntityConstraintRepository constraintRepository,
                                         DpEntityRepository entityRepository,
                                         DpFieldRepository fieldRepository,
                                         DpEntityConstraintMapper mapper) {
        super(constraintRepository);
        this.constraintRepository = constraintRepository;
        this.entityRepository = entityRepository;
        this.fieldRepository = fieldRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getEntityName() {
        return "DpEntityConstraint";
    }

    @Override
    protected DpEntityConstraintDto toDto(DpEntityConstraint entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected DpEntityConstraint toEntity(CreateDpEntityConstraintCmd command) {
        DpEntity entity = entityRepository.findActiveById(command.getEntityId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpEntity",
                        command.getEntityId()
                ));

        DpField field = resolveField(command.getFieldId());

        DpEntityConstraint constraint = DpEntityConstraint.create(
                entity,
                field,
                command.getConstraintCode(),
                command.getConstraintName(),
                toConstraintType(command.getConstraintType()),
                command.getExpressionJson()
        );

        constraint.applyMetadata(
                command.getConstraintName(),
                toConstraintType(command.getConstraintType()),
                command.getExpressionJson(),
                command.getIsActive() != null ? command.getIsActive() : Boolean.TRUE
        );

        return constraint;
    }

    @Override
    protected void updateEntity(DpEntityConstraint entity, UpdateDpEntityConstraintCmd command) {
        entity.applyMetadata(
                command.getConstraintName(),
                toConstraintType(command.getConstraintType()),
                command.getExpressionJson(),
                command.getIsActive()
        );
    }

    @Override
    protected void validateCreateCommand(CreateDpEntityConstraintCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getEntityId(), "entityId");
        validateNotNull(command.getConstraintCode(), "constraintCode");
        validateNotNull(command.getConstraintName(), "constraintName");
        validateNotNull(command.getConstraintType(), "constraintType");
        validateNotNull(command.getExpressionJson(), "expressionJson");
    }

    @Override
    protected void validateUpdateCommand(UpdateDpEntityConstraintCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getConstraintName(), "constraintName");
        validateNotNull(command.getConstraintType(), "constraintType");
        validateNotNull(command.getExpressionJson(), "expressionJson");
        validateNotNull(command.getIsActive(), "isActive");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateDpEntityConstraintCmd command) {
        if (constraintRepository.existsByEntity_IdAndConstraintCodeAndIsDeletedFalse(
                command.getEntityId(),
                command.getConstraintCode()
        )) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "DpEntityConstraint",
                    command.getConstraintCode()
            );
        }
    }

    @Override
    protected void beforeUpdate(DpEntityConstraint entity, UpdateDpEntityConstraintCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "DpEntityConstraint",
                    entity.getId()
            );
        }
    }

    @Override
    protected Specification<DpEntityConstraint> buildKeywordSpec(String keyword) {
        String pattern = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("constraintCode")), pattern),
                cb.like(cb.lower(root.get("constraintName")), pattern)
        );
    }

    @Override
    public List<DpEntityConstraintSummaryDto> findAllByEntity(Long entityId) {
        validateNotNull(entityId, "entityId");
        return mapper.toSummaryDtoList(
                constraintRepository.findAllByEntity_IdAndIsDeletedFalse(entityId)
        );
    }

    @Override
    public List<DpEntityConstraintSummaryDto> findActiveByEntity(Long entityId) {
        validateNotNull(entityId, "entityId");
        return mapper.toSummaryDtoList(
                constraintRepository.findAllByEntity_IdAndIsActiveTrueAndIsDeletedFalse(entityId)
        );
    }

    private DpField resolveField(Long fieldId) {
        if (fieldId == null) {
            return null;
        }

        return fieldRepository.findActiveById(fieldId)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpField",
                        fieldId
                ));
    }

    private ConstraintType toConstraintType(String raw) {
        try {
            return ConstraintType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception ex) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "constraintType", raw);
        }
    }
}
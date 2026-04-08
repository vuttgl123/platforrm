package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.entityrelation.*;
import com.meta_forge_platform.platform.application.mapper.DpEntityRelationMapper;
import com.meta_forge_platform.platform.application.service.DpEntityRelationService;
import com.meta_forge_platform.platform.domain.entity.DpEntity;
import com.meta_forge_platform.platform.domain.entity.DpEntityRelation;
import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.platform.domain.enumeration.RelationType;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRelationRepository;
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
public class DpEntityRelationServiceImpl
        extends BaseServiceImpl<DpEntityRelation, DpEntityRelationDto, CreateDpEntityRelationCmd, UpdateDpEntityRelationCmd, Long>
        implements DpEntityRelationService {

    private final DpEntityRelationRepository relationRepository;
    private final DpEntityRepository entityRepository;
    private final DpFieldRepository fieldRepository;
    private final DpEntityRelationMapper mapper;

    public DpEntityRelationServiceImpl(DpEntityRelationRepository relationRepository,
                                       DpEntityRepository entityRepository,
                                       DpFieldRepository fieldRepository,
                                       DpEntityRelationMapper mapper) {
        super(relationRepository);
        this.relationRepository = relationRepository;
        this.entityRepository = entityRepository;
        this.fieldRepository = fieldRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getEntityName() {
        return "DpEntityRelation";
    }

    @Override
    protected DpEntityRelationDto toDto(DpEntityRelation entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected DpEntityRelation toEntity(CreateDpEntityRelationCmd command) {

        DpEntity source = entityRepository.findActiveById(command.getSourceEntityId())
                .orElseThrow(() -> AppException.of(ErrorCode.ENTITY_NOT_FOUND, "DpEntity", command.getSourceEntityId()));

        DpEntity target = entityRepository.findActiveById(command.getTargetEntityId())
                .orElseThrow(() -> AppException.of(ErrorCode.ENTITY_NOT_FOUND, "DpEntity", command.getTargetEntityId()));

        DpEntityRelation relation = DpEntityRelation.create(
                source,
                target,
                command.getRelationCode(),
                command.getRelationName(),
                toType(command.getRelationType())
        );

        relation.applyMetadata(
                command.getRelationName(),
                toType(command.getRelationType()),
                resolveField(command.getOwnerFieldId()),
                resolveField(command.getInverseFieldId()),
                command.getConfigJson(),
                command.getIsActive() != null ? command.getIsActive() : true
        );

        return relation;
    }

    @Override
    protected void updateEntity(DpEntityRelation entity, UpdateDpEntityRelationCmd command) {
        entity.applyMetadata(
                command.getRelationName(),
                toType(command.getRelationType()),
                resolveField(command.getOwnerFieldId()),
                resolveField(command.getInverseFieldId()),
                command.getConfigJson(),
                command.getIsActive()
        );
    }

    @Override
    protected void beforeCreate(CreateDpEntityRelationCmd command) {
        if (relationRepository.existsBySourceEntity_IdAndRelationCodeAndIsDeletedFalse(
                command.getSourceEntityId(),
                command.getRelationCode()
        )) {
            throw AppException.of(ErrorCode.RECORD_DUPLICATE, "DpEntityRelation", command.getRelationCode());
        }
    }

    @Override
    protected void beforeUpdate(DpEntityRelation entity, UpdateDpEntityRelationCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(ErrorCode.OPTIMISTIC_LOCK, "DpEntityRelation", entity.getId());
        }
    }

    @Override
    protected Specification<DpEntityRelation> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("relationCode")), p),
                cb.like(cb.lower(root.get("relationName")), p)
        );
    }

    @Override
    public List<DpEntityRelationSummaryDto> findAllBySourceEntity(Long sourceEntityId) {
        return mapper.toSummaryDtoList(
                relationRepository.findAllBySourceEntity_IdAndIsDeletedFalse(sourceEntityId)
        );
    }

    @Override
    public List<DpEntityRelationSummaryDto> findAllByTargetEntity(Long targetEntityId) {
        return mapper.toSummaryDtoList(
                relationRepository.findAllByTargetEntity_IdAndIsDeletedFalse(targetEntityId)
        );
    }

    private DpField resolveField(Long id) {
        if (id == null) return null;
        return fieldRepository.findActiveById(id)
                .orElseThrow(() -> AppException.of(ErrorCode.ENTITY_NOT_FOUND, "DpField", id));
    }

    private RelationType toType(String raw) {
        try {
            return RelationType.valueOf(raw.toUpperCase());
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "relationType", raw);
        }
    }
}
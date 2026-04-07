package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.entityrelation.*;
import com.meta_forge_platform.platform.application.mapper.DpEntityRelationMapper;
import com.meta_forge_platform.platform.application.service.DpEntityRelationService;
import com.meta_forge_platform.platform.domain.entity.DpEntityRelation;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRelationRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpFieldRepository;
import com.meta_forge_platform.shared.application.BaseServiceImpl;
import com.meta_forge_platform.shared.domain.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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

    @Override protected DpEntityRelationDto toDto(DpEntityRelation e) { return mapper.toDto(e); }
    @Override protected DpEntityRelation toEntity(CreateDpEntityRelationCmd c) { return mapper.toEntity(c); }
    @Override protected void updateEntity(DpEntityRelation e, UpdateDpEntityRelationCmd c) { mapper.updateEntity(e, c); }

    @Override
    protected void afterToEntity(DpEntityRelation entity, CreateDpEntityRelationCmd cmd) {
        entity.setSourceEntity(entityRepository.findActiveById(cmd.getSourceEntityId())
                .orElseThrow(() -> AppException.notFound("SourceEntity", cmd.getSourceEntityId())));
        entity.setTargetEntity(entityRepository.findActiveById(cmd.getTargetEntityId())
                .orElseThrow(() -> AppException.notFound("TargetEntity", cmd.getTargetEntityId())));
        if (cmd.getMappedByFieldId() != null) {
            entity.setMappedByField(fieldRepository.findActiveById(cmd.getMappedByFieldId())
                    .orElseThrow(() -> AppException.notFound("Field", cmd.getMappedByFieldId())));
        }
    }

    @Override
    protected void beforeCreate(CreateDpEntityRelationCmd cmd) {
        if (relationRepository.existsBySourceEntity_IdAndRelationCodeAndIsDeletedFalse(
                cmd.getSourceEntityId(), cmd.getRelationCode()))
            throw AppException.conflict("Relation code đã tồn tại: " + cmd.getRelationCode());
    }

    @Override
    protected Specification<DpEntityRelation> buildKeywordSpec(String kw) {
        String p = "%" + kw.toLowerCase() + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("relationCode")), p),
                cb.like(cb.lower(root.get("relationName")), p)
        );
    }

    @Override
    public List<DpEntityRelationSummaryDto> findAllBySourceEntity(Long sourceEntityId) {
        return mapper.toSummaryDtoList(
                relationRepository.findAllBySourceEntity_IdAndIsDeletedFalse(sourceEntityId));
    }

    @Override
    public List<DpEntityRelationSummaryDto> findAllByTargetEntity(Long targetEntityId) {
        return mapper.toSummaryDtoList(
                relationRepository.findAllByTargetEntity_IdAndIsDeletedFalse(targetEntityId));
    }
}
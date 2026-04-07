package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.field.*;
import com.meta_forge_platform.platform.application.mapper.DpFieldMapper;
import com.meta_forge_platform.platform.application.service.DpFieldService;
import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpFieldGroupRepository;
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

    @Override protected DpFieldDto toDto(DpField e) { return mapper.toDto(e); }
    @Override protected DpField toEntity(CreateDpFieldCmd c) { return mapper.toEntity(c); }
    @Override protected void updateEntity(DpField e, UpdateDpFieldCmd c) { mapper.updateEntity(e, c); }

    @Override
    protected void afterToEntity(DpField entity, CreateDpFieldCmd cmd) {
        entity.setEntity(entityRepository.findActiveById(cmd.getEntityId())
                .orElseThrow(() -> AppException.notFound("Entity", cmd.getEntityId())));
        if (cmd.getFieldGroupId() != null) {
            entity.setFieldGroup(fieldGroupRepository.findActiveById(cmd.getFieldGroupId())
                    .orElseThrow(() -> AppException.notFound("FieldGroup", cmd.getFieldGroupId())));
        }
        if (cmd.getRelationEntityId() != null) {
            entity.setRelationEntity(entityRepository.findActiveById(cmd.getRelationEntityId())
                    .orElseThrow(() -> AppException.notFound("RelationEntity", cmd.getRelationEntityId())));
        }
    }

    @Override
    protected void beforeCreate(CreateDpFieldCmd cmd) {
        if (fieldRepository.existsByEntity_IdAndFieldCodeAndIsDeletedFalse(
                cmd.getEntityId(), cmd.getFieldCode()))
            throw AppException.conflict("Field code đã tồn tại: " + cmd.getFieldCode());
    }

    @Override
    protected void beforeDelete(DpField entity) {
        if (Boolean.TRUE.equals(entity.getIsSystem()))
            throw AppException.badRequest("Không thể xóa field hệ thống: " + entity.getFieldCode());
    }

    @Override
    protected Specification<DpField> buildKeywordSpec(String kw) {
        String p = "%" + kw.toLowerCase() + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("fieldCode")), p),
                cb.like(cb.lower(root.get("fieldName")), p)
        );
    }

    @Override
    public List<DpFieldSummaryDto> findAllByEntity(Long entityId) {
        return mapper.toSummaryDtoList(
                fieldRepository.findAllByEntity_IdAndIsDeletedFalseOrderBySortOrderAsc(entityId));
    }

    @Override
    public List<DpFieldSummaryDto> findAllByFieldGroup(Long groupId) {
        return mapper.toSummaryDtoList(
                fieldRepository.findAllByFieldGroup_IdAndIsDeletedFalseOrderBySortOrderAsc(groupId));
    }

    @Override
    public List<DpFieldSummaryDto> findSearchableFields(Long entityId) {
        return mapper.toSummaryDtoList(fieldRepository.findSearchableFields(entityId));
    }
}
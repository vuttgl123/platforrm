package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.fieldgroup.*;
import com.meta_forge_platform.platform.application.mapper.DpFieldGroupMapper;
import com.meta_forge_platform.platform.application.service.DpFieldGroupService;
import com.meta_forge_platform.platform.domain.entity.DpFieldGroup;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpFieldGroupRepository;
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
public class DpFieldGroupServiceImpl
        extends BaseServiceImpl<DpFieldGroup, DpFieldGroupDto, CreateDpFieldGroupCmd, UpdateDpFieldGroupCmd, Long>
        implements DpFieldGroupService {

    private final DpFieldGroupRepository fieldGroupRepository;
    private final DpEntityRepository entityRepository;
    private final DpFieldGroupMapper mapper;

    public DpFieldGroupServiceImpl(DpFieldGroupRepository fieldGroupRepository,
                                   DpEntityRepository entityRepository,
                                   DpFieldGroupMapper mapper) {
        super(fieldGroupRepository);
        this.fieldGroupRepository = fieldGroupRepository;
        this.entityRepository = entityRepository;
        this.mapper = mapper;
    }

    @Override protected DpFieldGroupDto toDto(DpFieldGroup e) { return mapper.toDto(e); }
    @Override protected DpFieldGroup toEntity(CreateDpFieldGroupCmd c) { return mapper.toEntity(c); }
    @Override protected void updateEntity(DpFieldGroup e, UpdateDpFieldGroupCmd c) { mapper.updateEntity(e, c); }

    @Override
    protected void afterToEntity(DpFieldGroup entity, CreateDpFieldGroupCmd cmd) {
        entity.setEntity(entityRepository.findActiveById(cmd.getEntityId())
                .orElseThrow(() -> AppException.notFound("Entity", cmd.getEntityId())));
    }

    @Override
    protected void beforeCreate(CreateDpFieldGroupCmd cmd) {
        if (fieldGroupRepository.existsByEntity_IdAndGroupCodeAndIsDeletedFalse(
                cmd.getEntityId(), cmd.getGroupCode()))
            throw AppException.conflict("Group code đã tồn tại: " + cmd.getGroupCode());
    }

    @Override
    protected Specification<DpFieldGroup> buildKeywordSpec(String kw) {
        String p = "%" + kw.toLowerCase() + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("groupCode")), p),
                cb.like(cb.lower(root.get("groupName")), p)
        );
    }

    @Override
    public List<DpFieldGroupSummaryDto> findAllByEntity(Long entityId) {
        return mapper.toSummaryDtoList(
                fieldGroupRepository.findAllByEntity_IdAndIsDeletedFalseOrderBySortOrderAsc(entityId));
    }
}
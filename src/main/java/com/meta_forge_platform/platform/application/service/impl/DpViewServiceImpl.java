package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.view.*;
import com.meta_forge_platform.platform.application.mapper.DpViewMapper;
import com.meta_forge_platform.platform.application.service.DpViewService;
import com.meta_forge_platform.platform.domain.entity.DpView;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpViewRepository;
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
public class DpViewServiceImpl
        extends BaseServiceImpl<DpView, DpViewDto, CreateDpViewCmd, UpdateDpViewCmd, Long>
        implements DpViewService {

    private final DpViewRepository viewRepository;
    private final DpEntityRepository entityRepository;
    private final DpViewMapper mapper;

    public DpViewServiceImpl(DpViewRepository viewRepository,
                             DpEntityRepository entityRepository,
                             DpViewMapper mapper) {
        super(viewRepository);
        this.viewRepository = viewRepository;
        this.entityRepository = entityRepository;
        this.mapper = mapper;
    }

    @Override protected DpViewDto toDto(DpView e) { return mapper.toDto(e); }
    @Override protected DpView toEntity(CreateDpViewCmd c) { return mapper.toEntity(c); }
    @Override protected void updateEntity(DpView e, UpdateDpViewCmd c) { mapper.updateEntity(e, c); }

    @Override
    protected void afterToEntity(DpView entity, CreateDpViewCmd cmd) {
        entity.setEntity(entityRepository.findActiveById(cmd.getEntityId())
                .orElseThrow(() -> AppException.notFound("Entity", cmd.getEntityId())));
    }

    @Override
    protected void beforeCreate(CreateDpViewCmd cmd) {
        if (viewRepository.existsByEntity_IdAndViewCodeAndIsDeletedFalse(
                cmd.getEntityId(), cmd.getViewCode()))
            throw AppException.conflict("View code đã tồn tại: " + cmd.getViewCode());
    }

    @Override
    protected Specification<DpView> buildKeywordSpec(String kw) {
        String p = "%" + kw.toLowerCase() + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("viewCode")), p),
                cb.like(cb.lower(root.get("viewName")), p)
        );
    }

    @Override
    public List<DpViewSummaryDto> findAllByEntity(Long entityId) {
        return mapper.toSummaryDtoList(
                viewRepository.findAllByEntity_IdAndIsDeletedFalse(entityId));
    }

    @Override
    public DpViewDto getDefaultByEntity(Long entityId) {
        return viewRepository.findByEntity_IdAndIsDefaultTrueAndIsDeletedFalse(entityId)
                .map(mapper::toDto)
                .orElseThrow(() -> AppException.notFound("DefaultView cho Entity", entityId));
    }
}
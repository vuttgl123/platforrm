package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.screen.*;
import com.meta_forge_platform.platform.application.mapper.DpScreenMapper;
import com.meta_forge_platform.platform.application.service.DpScreenService;
import com.meta_forge_platform.platform.domain.entity.DpScreen;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpModuleRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpScreenRepository;
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
public class DpScreenServiceImpl
        extends BaseServiceImpl<DpScreen, DpScreenDto, CreateDpScreenCmd, UpdateDpScreenCmd, Long>
        implements DpScreenService {

    private final DpScreenRepository screenRepository;
    private final DpModuleRepository moduleRepository;
    private final DpEntityRepository entityRepository;
    private final DpScreenMapper mapper;

    public DpScreenServiceImpl(DpScreenRepository screenRepository,
                               DpModuleRepository moduleRepository,
                               DpEntityRepository entityRepository,
                               DpScreenMapper mapper) {
        super(screenRepository);
        this.screenRepository = screenRepository;
        this.moduleRepository = moduleRepository;
        this.entityRepository = entityRepository;
        this.mapper = mapper;
    }

    @Override protected DpScreenDto toDto(DpScreen e) { return mapper.toDto(e); }
    @Override protected DpScreen toEntity(CreateDpScreenCmd c) { return mapper.toEntity(c); }
    @Override protected void updateEntity(DpScreen e, UpdateDpScreenCmd c) { mapper.updateEntity(e, c); }

    @Override
    protected void afterToEntity(DpScreen entity, CreateDpScreenCmd cmd) {
        entity.setModule(moduleRepository.findActiveById(cmd.getModuleId())
                .orElseThrow(() -> AppException.notFound("Module", cmd.getModuleId())));
        if (cmd.getEntityId() != null) {
            entity.setEntity(entityRepository.findActiveById(cmd.getEntityId())
                    .orElseThrow(() -> AppException.notFound("Entity", cmd.getEntityId())));
        }
    }

    @Override
    protected void beforeCreate(CreateDpScreenCmd cmd) {
        if (screenRepository.existsByModule_IdAndScreenCodeAndIsDeletedFalse(
                cmd.getModuleId(), cmd.getScreenCode()))
            throw AppException.conflict("Screen code đã tồn tại: " + cmd.getScreenCode());
    }

    @Override
    protected Specification<DpScreen> buildKeywordSpec(String kw) {
        String p = "%" + kw.toLowerCase() + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("screenCode")), p),
                cb.like(cb.lower(root.get("screenName")), p)
        );
    }

    @Override
    public List<DpScreenSummaryDto> findAllByModule(Long moduleId) {
        return mapper.toSummaryDtoList(
                screenRepository.findAllByModule_IdAndIsDeletedFalseOrderBySortOrderAsc(moduleId));
    }

    @Override
    public List<DpScreenSummaryDto> findAllByEntity(Long entityId) {
        return mapper.toSummaryDtoList(
                screenRepository.findAllByEntity_IdAndIsDeletedFalse(entityId));
    }
}
package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.module.*;
import com.meta_forge_platform.platform.application.mapper.DpModuleMapper;
import com.meta_forge_platform.platform.application.service.DpModuleService;
import com.meta_forge_platform.platform.domain.entity.DpModule;
import com.meta_forge_platform.platform.infrastructure.repository.DpModuleRepository;
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
public class DpModuleServiceImpl
        extends BaseServiceImpl<DpModule, DpModuleDto, CreateDpModuleCmd, UpdateDpModuleCmd, Long>
        implements DpModuleService {

    private final DpModuleRepository moduleRepository;
    private final DpModuleMapper mapper;

    public DpModuleServiceImpl(DpModuleRepository moduleRepository, DpModuleMapper mapper) {
        super(moduleRepository);
        this.moduleRepository = moduleRepository;
        this.mapper = mapper;
    }

    @Override protected DpModuleDto toDto(DpModule e) { return mapper.toDto(e); }
    @Override protected DpModule toEntity(CreateDpModuleCmd c) { return mapper.toEntity(c); }
    @Override protected void updateEntity(DpModule e, UpdateDpModuleCmd c) { mapper.updateEntity(e, c); }

    @Override
    protected Specification<DpModule> buildKeywordSpec(String kw) {
        String p = "%" + kw.toLowerCase() + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("moduleCode")), p),
                cb.like(cb.lower(root.get("moduleName")), p),
                cb.like(cb.lower(root.get("description")), p)
        );
    }

    @Override
    protected void beforeCreate(CreateDpModuleCmd cmd) {
        if (moduleRepository.existsByModuleCodeAndIsDeletedFalse(cmd.getModuleCode()))
            throw AppException.conflict("Module code đã tồn tại: " + cmd.getModuleCode());
    }

    @Override
    protected void beforeDelete(DpModule entity) {
        if (Boolean.TRUE.equals(entity.getIsSystem()))
            throw AppException.badRequest("Không thể xóa module hệ thống: " + entity.getModuleCode());
    }

    @Override
    public DpModuleDto getByCode(String moduleCode) {
        return moduleRepository.findByModuleCodeAndIsDeletedFalse(moduleCode)
                .map(mapper::toDto)
                .orElseThrow(() -> AppException.notFound("Module", moduleCode));
    }

    @Override
    public List<DpModuleSummaryDto> findAllSummary() {
        return mapper.toSummaryDtoList(moduleRepository.findAllActive());
    }

    @Override
    public List<DpModuleSummaryDto> findAllActiveSummary() {
        return mapper.toSummaryDtoList(moduleRepository.findAllActiveSorted());
    }
}
package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.module.*;
import com.meta_forge_platform.platform.application.mapper.DpModuleMapper;
import com.meta_forge_platform.platform.application.service.DpModuleService;
import com.meta_forge_platform.platform.domain.entity.DpModule;
import com.meta_forge_platform.platform.domain.enumeration.ModuleStatus;
import com.meta_forge_platform.platform.infrastructure.repository.DpModuleRepository;
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

    @Override
    protected String getEntityName() {
        return "DpModule";
    }

    @Override
    protected DpModuleDto toDto(DpModule entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected DpModule toEntity(CreateDpModuleCmd command) {
        DpModule module = DpModule.create(
                command.getModuleCode(),
                command.getModuleName()
        );

        module.applyMetadata(
                command.getModuleName(),
                command.getDescription(),
                toStatus(command.getStatus()),
                command.getSortOrder(),
                command.getIsSystem()
        );

        return module;
    }

    @Override
    protected void updateEntity(DpModule entity, UpdateDpModuleCmd command) {
        entity.applyMetadata(
                command.getModuleName(),
                command.getDescription(),
                toStatus(command.getStatus()),
                command.getSortOrder(),
                command.getIsSystem()
        );
    }

    @Override
    protected void validateCreateCommand(CreateDpModuleCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getModuleCode(), "moduleCode");
        validateNotNull(command.getModuleName(), "moduleName");
        validateNotNull(command.getStatus(), "status");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getIsSystem(), "isSystem");
    }

    @Override
    protected void validateUpdateCommand(UpdateDpModuleCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getModuleName(), "moduleName");
        validateNotNull(command.getStatus(), "status");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getIsSystem(), "isSystem");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateDpModuleCmd command) {
        if (moduleRepository.existsByModuleCodeAndIsDeletedFalse(command.getModuleCode())) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "DpModule",
                    command.getModuleCode()
            );
        }
    }

    @Override
    protected void beforeUpdate(DpModule entity, UpdateDpModuleCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "DpModule",
                    entity.getId()
            );
        }
    }

    @Override
    protected void beforeDelete(DpModule entity) {
        if (Boolean.TRUE.equals(entity.getIsSystem())) {
            throw AppException.of(
                    ErrorCode.FORBIDDEN,
                    "SYSTEM_MODULE"
            );
        }
    }

    @Override
    protected Specification<DpModule> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("moduleCode")), p),
                cb.like(cb.lower(root.get("moduleName")), p),
                cb.like(cb.lower(cb.coalesce(root.get("description"), "")), p)
        );
    }

    @Override
    public DpModuleDto getByCode(String moduleCode) {
        return moduleRepository.findByModuleCodeAndIsDeletedFalse(moduleCode)
                .map(mapper::toDto)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpModule",
                        moduleCode
                ));
    }

    @Override
    public List<DpModuleSummaryDto> findAllSummary() {
        return mapper.toSummaryDtoList(moduleRepository.findAllActive());
    }

    @Override
    public List<DpModuleSummaryDto> findAllActiveSummary() {
        return mapper.toSummaryDtoList(moduleRepository.findAllActiveSorted());
    }

    private ModuleStatus toStatus(String raw) {
        try {
            return ModuleStatus.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "status", raw);
        }
    }
}
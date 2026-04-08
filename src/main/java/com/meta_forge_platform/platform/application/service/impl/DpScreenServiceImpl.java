package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.screen.*;
import com.meta_forge_platform.platform.application.mapper.DpScreenMapper;
import com.meta_forge_platform.platform.application.service.DpScreenService;
import com.meta_forge_platform.platform.domain.entity.DpEntity;
import com.meta_forge_platform.platform.domain.entity.DpModule;
import com.meta_forge_platform.platform.domain.entity.DpScreen;
import com.meta_forge_platform.platform.domain.enumeration.ScreenType;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpModuleRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpScreenRepository;
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

    @Override
    protected String getEntityName() {
        return "DpScreen";
    }

    @Override
    protected DpScreenDto toDto(DpScreen entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected DpScreen toEntity(CreateDpScreenCmd command) {
        DpModule module = moduleRepository.findActiveById(command.getModuleId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpModule",
                        command.getModuleId()
                ));

        DpEntity entity = resolveEntity(command.getEntityId());

        DpScreen screen = DpScreen.create(
                module,
                entity,
                command.getScreenCode(),
                command.getScreenName(),
                toScreenType(command.getScreenType())
        );

        screen.applyMetadata(
                command.getScreenName(),
                toScreenType(command.getScreenType()),
                command.getRoutePath(),
                command.getIsActive(),
                command.getSortOrder(),
                command.getConfigJson()
        );

        return screen;
    }

    @Override
    protected void updateEntity(DpScreen entity, UpdateDpScreenCmd command) {
        entity.applyMetadata(
                command.getScreenName(),
                toScreenType(command.getScreenType()),
                command.getRoutePath(),
                command.getIsActive(),
                command.getSortOrder(),
                command.getConfigJson()
        );
    }

    @Override
    protected void validateCreateCommand(CreateDpScreenCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getModuleId(), "moduleId");
        validateNotNull(command.getScreenCode(), "screenCode");
        validateNotNull(command.getScreenName(), "screenName");
        validateNotNull(command.getScreenType(), "screenType");
        validateNotNull(command.getIsActive(), "isActive");
        validateNotNull(command.getSortOrder(), "sortOrder");
    }

    @Override
    protected void validateUpdateCommand(UpdateDpScreenCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getScreenName(), "screenName");
        validateNotNull(command.getScreenType(), "screenType");
        validateNotNull(command.getIsActive(), "isActive");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateDpScreenCmd command) {
        if (screenRepository.existsByModule_IdAndScreenCodeAndIsDeletedFalse(
                command.getModuleId(),
                command.getScreenCode()
        )) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "DpScreen",
                    command.getScreenCode()
            );
        }
    }

    @Override
    protected void beforeUpdate(DpScreen entity, UpdateDpScreenCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "DpScreen",
                    entity.getId()
            );
        }
    }

    @Override
    protected Specification<DpScreen> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("screenCode")), p),
                cb.like(cb.lower(root.get("screenName")), p)
        );
    }

    @Override
    public List<DpScreenSummaryDto> findAllByModule(Long moduleId) {
        return mapper.toSummaryDtoList(
                screenRepository.findAllByModule_IdAndIsDeletedFalseOrderBySortOrderAsc(moduleId)
        );
    }

    @Override
    public List<DpScreenSummaryDto> findAllByEntity(Long entityId) {
        return mapper.toSummaryDtoList(
                screenRepository.findAllByEntity_IdAndIsDeletedFalseOrderBySortOrderAsc(entityId)
        );
    }

    private DpEntity resolveEntity(Long id) {
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

    private ScreenType toScreenType(String raw) {
        try {
            return ScreenType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "screenType", raw);
        }
    }
}
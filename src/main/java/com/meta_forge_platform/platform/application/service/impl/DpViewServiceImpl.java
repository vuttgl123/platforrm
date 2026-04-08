package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.view.*;
import com.meta_forge_platform.platform.application.mapper.DpViewMapper;
import com.meta_forge_platform.platform.application.service.DpViewService;
import com.meta_forge_platform.platform.domain.entity.DpEntity;
import com.meta_forge_platform.platform.domain.entity.DpView;
import com.meta_forge_platform.platform.domain.enumeration.ViewType;
import com.meta_forge_platform.platform.domain.enumeration.ViewVisibility;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpViewRepository;
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

    @Override
    protected String getEntityName() {
        return "DpView";
    }

    @Override
    protected DpViewDto toDto(DpView entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected DpView toEntity(CreateDpViewCmd command) {
        DpEntity entity = entityRepository.findActiveById(command.getEntityId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpEntity",
                        command.getEntityId()
                ));

        DpView view = DpView.create(
                entity,
                command.getViewCode(),
                command.getViewName(),
                toViewType(command.getViewType()),
                command.getQueryJson(),
                command.getColumnsJson()
        );

        view.applyMetadata(
                command.getViewName(),
                toViewType(command.getViewType()),
                command.getQueryJson(),
                command.getColumnsJson(),
                toVisibility(command.getVisibility()),
                command.getSortOrder(),
                command.getIsDefault(),
                command.getIsActive()
        );

        return view;
    }

    @Override
    protected void updateEntity(DpView entity, UpdateDpViewCmd command) {
        entity.applyMetadata(
                command.getViewName(),
                toViewType(command.getViewType()),
                command.getQueryJson(),
                command.getColumnsJson(),
                toVisibility(command.getVisibility()),
                command.getSortOrder(),
                command.getIsDefault(),
                command.getIsActive()
        );
    }

    @Override
    protected void validateCreateCommand(CreateDpViewCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getEntityId(), "entityId");
        validateNotNull(command.getViewCode(), "viewCode");
        validateNotNull(command.getViewName(), "viewName");
        validateNotNull(command.getViewType(), "viewType");
        validateNotNull(command.getQueryJson(), "queryJson");
        validateNotNull(command.getVisibility(), "visibility");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getIsDefault(), "isDefault");
        validateNotNull(command.getIsActive(), "isActive");
    }

    @Override
    protected void validateUpdateCommand(UpdateDpViewCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getViewName(), "viewName");
        validateNotNull(command.getViewType(), "viewType");
        validateNotNull(command.getQueryJson(), "queryJson");
        validateNotNull(command.getVisibility(), "visibility");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getIsDefault(), "isDefault");
        validateNotNull(command.getIsActive(), "isActive");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateDpViewCmd command) {
        if (viewRepository.existsByEntity_IdAndViewCodeAndIsDeletedFalse(
                command.getEntityId(),
                command.getViewCode()
        )) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "DpView",
                    command.getViewCode()
            );
        }

        if (Boolean.TRUE.equals(command.getIsDefault())) {
            viewRepository.unsetAllDefaultByEntityId(command.getEntityId());
        }
    }

    @Override
    protected void beforeUpdate(DpView entity, UpdateDpViewCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "DpView",
                    entity.getId()
            );
        }

        if (Boolean.TRUE.equals(command.getIsDefault())) {
            viewRepository.unsetDefaultExcept(entity.getEntity().getId(), entity.getId());
        }
    }

    @Override
    protected Specification<DpView> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("viewCode")), p),
                cb.like(cb.lower(root.get("viewName")), p)
        );
    }

    @Override
    public List<DpViewSummaryDto> findAllByEntity(Long entityId) {
        return mapper.toSummaryDtoList(
                viewRepository.findAllByEntity_IdAndIsDeletedFalseOrderBySortOrderAsc(entityId)
        );
    }

    @Override
    public DpViewDto getDefaultByEntity(Long entityId) {
        return viewRepository.findByEntity_IdAndIsDefaultTrueAndIsDeletedFalse(entityId)
                .map(mapper::toDto)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DefaultView",
                        entityId
                ));
    }

    private ViewType toViewType(String raw) {
        try {
            return ViewType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "viewType", raw);
        }
    }

    private ViewVisibility toVisibility(String raw) {
        try {
            return ViewVisibility.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "visibility", raw);
        }
    }
}
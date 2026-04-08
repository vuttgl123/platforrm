package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.screenfield.*;
import com.meta_forge_platform.platform.application.mapper.DpScreenFieldMapper;
import com.meta_forge_platform.platform.application.service.DpScreenFieldService;
import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.platform.domain.entity.DpScreen;
import com.meta_forge_platform.platform.domain.entity.DpScreenField;
import com.meta_forge_platform.platform.domain.entity.DpScreenSection;
import com.meta_forge_platform.platform.domain.enumeration.ScreenWidgetType;
import com.meta_forge_platform.platform.infrastructure.repository.DpFieldRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpScreenFieldRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpScreenRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpScreenSectionRepository;
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
public class DpScreenFieldServiceImpl
        extends BaseServiceImpl<DpScreenField, DpScreenFieldDto, CreateDpScreenFieldCmd, UpdateDpScreenFieldCmd, Long>
        implements DpScreenFieldService {

    private final DpScreenFieldRepository screenFieldRepository;
    private final DpScreenRepository screenRepository;
    private final DpScreenSectionRepository sectionRepository;
    private final DpFieldRepository fieldRepository;
    private final DpScreenFieldMapper mapper;

    public DpScreenFieldServiceImpl(DpScreenFieldRepository screenFieldRepository,
                                    DpScreenRepository screenRepository,
                                    DpScreenSectionRepository sectionRepository,
                                    DpFieldRepository fieldRepository,
                                    DpScreenFieldMapper mapper) {
        super(screenFieldRepository);
        this.screenFieldRepository = screenFieldRepository;
        this.screenRepository = screenRepository;
        this.sectionRepository = sectionRepository;
        this.fieldRepository = fieldRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getEntityName() {
        return "DpScreenField";
    }

    @Override
    protected DpScreenFieldDto toDto(DpScreenField entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected DpScreenField toEntity(CreateDpScreenFieldCmd command) {
        DpScreen screen = screenRepository.findActiveById(command.getScreenId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpScreen",
                        command.getScreenId()
                ));

        DpField field = fieldRepository.findActiveById(command.getFieldId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpField",
                        command.getFieldId()
                ));

        DpScreenSection section = resolveSection(command.getSectionId());

        DpScreenField screenField = DpScreenField.create(screen, field);

        screenField.applyMetadata(
                section,
                command.getDisplayLabel(),
                toWidgetType(command.getWidgetType()),
                command.getColSpan(),
                command.getRowNo(),
                command.getSortOrder(),
                command.getIsReadonly(),
                command.getIsHidden(),
                command.getConfigJson()
        );

        return screenField;
    }

    @Override
    protected void updateEntity(DpScreenField entity, UpdateDpScreenFieldCmd command) {
        DpScreenSection section = resolveSection(command.getSectionId());

        screenFieldValidation(entity.getScreen().getId(), entity.getField().getId(), section, entity.getId());

        entity.applyMetadata(
                section,
                command.getDisplayLabel(),
                toWidgetType(command.getWidgetType()),
                command.getColSpan(),
                command.getRowNo(),
                command.getSortOrder(),
                command.getIsReadonly(),
                command.getIsHidden(),
                command.getConfigJson()
        );
    }

    @Override
    protected void validateCreateCommand(CreateDpScreenFieldCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getScreenId(), "screenId");
        validateNotNull(command.getFieldId(), "fieldId");
        validateNotNull(command.getColSpan(), "colSpan");
        validateNotNull(command.getRowNo(), "rowNo");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getIsReadonly(), "isReadonly");
        validateNotNull(command.getIsHidden(), "isHidden");
    }

    @Override
    protected void validateUpdateCommand(UpdateDpScreenFieldCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getColSpan(), "colSpan");
        validateNotNull(command.getRowNo(), "rowNo");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getIsReadonly(), "isReadonly");
        validateNotNull(command.getIsHidden(), "isHidden");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateDpScreenFieldCmd command) {
        if (screenFieldRepository.existsByScreen_IdAndField_IdAndSection_IdAndIsDeletedFalse(
                command.getScreenId(),
                command.getFieldId(),
                command.getSectionId()
        )) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "DpScreenField",
                    command.getFieldId()
            );
        }
    }

    @Override
    protected void beforeUpdate(DpScreenField entity, UpdateDpScreenFieldCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "DpScreenField",
                    entity.getId()
            );
        }
    }

    @Override
    protected Specification<DpScreenField> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.like(
                cb.lower(cb.coalesce(root.get("displayLabel"), "")),
                p
        );
    }

    @Override
    public List<DpScreenFieldSummaryDto> findAllByScreen(Long screenId) {
        return mapper.toSummaryDtoList(
                screenFieldRepository.findAllByScreen_IdAndIsDeletedFalseOrderBySortOrderAsc(screenId)
        );
    }

    @Override
    public List<DpScreenFieldSummaryDto> findAllBySection(Long sectionId) {
        return mapper.toSummaryDtoList(
                screenFieldRepository.findAllBySection_IdAndIsDeletedFalseOrderBySortOrderAsc(sectionId)
        );
    }

    private DpScreenSection resolveSection(Long id) {
        if (id == null) {
            return null;
        }
        return sectionRepository.findActiveById(id)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpScreenSection",
                        id
                ));
    }

    private void screenFieldValidation(Long screenId, Long fieldId, DpScreenSection section, Long currentId) {
        Long sectionId = section != null ? section.getId() : null;

        screenFieldRepository.findByScreen_IdAndField_IdAndSection_IdAndIsDeletedFalse(screenId, fieldId, sectionId)
                .filter(found -> !Objects.equals(found.getId(), currentId))
                .ifPresent(found -> {
                    throw AppException.of(ErrorCode.RECORD_DUPLICATE, "DpScreenField", fieldId);
                });
    }

    private ScreenWidgetType toWidgetType(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return ScreenWidgetType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "widgetType", raw);
        }
    }
}
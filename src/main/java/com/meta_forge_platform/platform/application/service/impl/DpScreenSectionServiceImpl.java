package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.screensection.*;
import com.meta_forge_platform.platform.application.mapper.DpScreenSectionMapper;
import com.meta_forge_platform.platform.application.service.DpScreenSectionService;
import com.meta_forge_platform.platform.domain.entity.DpScreen;
import com.meta_forge_platform.platform.domain.entity.DpScreenSection;
import com.meta_forge_platform.platform.domain.enumeration.ScreenSectionType;
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
public class DpScreenSectionServiceImpl
        extends BaseServiceImpl<DpScreenSection, DpScreenSectionDto, CreateDpScreenSectionCmd, UpdateDpScreenSectionCmd, Long>
        implements DpScreenSectionService {

    private final DpScreenSectionRepository sectionRepository;
    private final DpScreenRepository screenRepository;
    private final DpScreenSectionMapper mapper;

    public DpScreenSectionServiceImpl(DpScreenSectionRepository sectionRepository,
                                      DpScreenRepository screenRepository,
                                      DpScreenSectionMapper mapper) {
        super(sectionRepository);
        this.sectionRepository = sectionRepository;
        this.screenRepository = screenRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getEntityName() {
        return "DpScreenSection";
    }

    @Override
    protected DpScreenSectionDto toDto(DpScreenSection entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected DpScreenSection toEntity(CreateDpScreenSectionCmd command) {
        DpScreen screen = screenRepository.findActiveById(command.getScreenId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpScreen",
                        command.getScreenId()
                ));

        DpScreenSection parentSection = resolveParentSection(command.getParentSectionId());

        DpScreenSection section = DpScreenSection.create(
                screen,
                command.getSectionCode(),
                command.getSectionName(),
                toSectionType(command.getSectionType())
        );

        section.applyMetadata(
                command.getSectionName(),
                toSectionType(command.getSectionType()),
                parentSection,
                command.getSortOrder(),
                command.getConfigJson()
        );

        return section;
    }

    @Override
    protected void updateEntity(DpScreenSection entity, UpdateDpScreenSectionCmd command) {
        DpScreenSection parentSection = resolveParentSection(command.getParentSectionId());
        validateParentNotSelf(entity.getId(), parentSection);

        entity.applyMetadata(
                command.getSectionName(),
                toSectionType(command.getSectionType()),
                parentSection,
                command.getSortOrder(),
                command.getConfigJson()
        );
    }

    @Override
    protected void validateCreateCommand(CreateDpScreenSectionCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getScreenId(), "screenId");
        validateNotNull(command.getSectionCode(), "sectionCode");
        validateNotNull(command.getSectionName(), "sectionName");
        validateNotNull(command.getSectionType(), "sectionType");
        validateNotNull(command.getSortOrder(), "sortOrder");
    }

    @Override
    protected void validateUpdateCommand(UpdateDpScreenSectionCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getSectionName(), "sectionName");
        validateNotNull(command.getSectionType(), "sectionType");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateDpScreenSectionCmd command) {
        if (sectionRepository.existsByScreen_IdAndSectionCodeAndIsDeletedFalse(
                command.getScreenId(),
                command.getSectionCode()
        )) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "DpScreenSection",
                    command.getSectionCode()
            );
        }
    }

    @Override
    protected void beforeUpdate(DpScreenSection entity, UpdateDpScreenSectionCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "DpScreenSection",
                    entity.getId()
            );
        }
    }

    @Override
    protected Specification<DpScreenSection> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("sectionCode")), p),
                cb.like(cb.lower(root.get("sectionName")), p)
        );
    }

    @Override
    public List<DpScreenSectionSummaryDto> findAllByScreen(Long screenId) {
        return mapper.toSummaryDtoList(
                sectionRepository.findAllByScreen_IdAndIsDeletedFalseOrderBySortOrderAsc(screenId)
        );
    }

    @Override
    public List<DpScreenSectionSummaryDto> findRootSectionsByScreen(Long screenId) {
        return mapper.toSummaryDtoList(
                sectionRepository.findAllByScreen_IdAndParentSectionIsNullAndIsDeletedFalseOrderBySortOrderAsc(screenId)
        );
    }

    @Override
    public List<DpScreenSectionSummaryDto> findChildSections(Long parentSectionId) {
        return mapper.toSummaryDtoList(
                sectionRepository.findAllByParentSection_IdAndIsDeletedFalseOrderBySortOrderAsc(parentSectionId)
        );
    }

    private DpScreenSection resolveParentSection(Long id) {
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

    private void validateParentNotSelf(Long currentId, DpScreenSection parentSection) {
        if (currentId != null && parentSection != null && Objects.equals(currentId, parentSection.getId())) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "parentSectionId", currentId);
        }
    }

    private ScreenSectionType toSectionType(String raw) {
        try {
            return ScreenSectionType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "sectionType", raw);
        }
    }
}
package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.screensection.*;
import com.meta_forge_platform.platform.application.mapper.DpScreenSectionMapper;
import com.meta_forge_platform.platform.application.service.DpScreenSectionService;
import com.meta_forge_platform.platform.domain.entity.DpScreenSection;
import com.meta_forge_platform.platform.infrastructure.repository.DpScreenRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpScreenSectionRepository;
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

    @Override protected DpScreenSectionDto toDto(DpScreenSection e) { return mapper.toDto(e); }
    @Override protected DpScreenSection toEntity(CreateDpScreenSectionCmd c) { return mapper.toEntity(c); }
    @Override protected void updateEntity(DpScreenSection e, UpdateDpScreenSectionCmd c) { mapper.updateEntity(e, c); }

    @Override
    protected void afterToEntity(DpScreenSection entity, CreateDpScreenSectionCmd cmd) {
        entity.setScreen(screenRepository.findActiveById(cmd.getScreenId())
                .orElseThrow(() -> AppException.notFound("Screen", cmd.getScreenId())));
        if (cmd.getParentSectionId() != null) {
            entity.setParentSection(sectionRepository.findActiveById(cmd.getParentSectionId())
                    .orElseThrow(() -> AppException.notFound("ParentSection", cmd.getParentSectionId())));
        }
    }

    @Override
    protected void beforeCreate(CreateDpScreenSectionCmd cmd) {
        if (sectionRepository.existsByScreen_IdAndSectionCodeAndIsDeletedFalse(
                cmd.getScreenId(), cmd.getSectionCode()))
            throw AppException.conflict("Section code đã tồn tại: " + cmd.getSectionCode());
    }

    @Override
    protected Specification<DpScreenSection> buildKeywordSpec(String kw) {
        String p = "%" + kw.toLowerCase() + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("sectionCode")), p),
                cb.like(cb.lower(root.get("sectionName")), p)
        );
    }

    @Override
    public List<DpScreenSectionSummaryDto> findAllByScreen(Long screenId) {
        return mapper.toSummaryDtoList(
                sectionRepository.findAllByScreen_IdAndIsDeletedFalseOrderBySortOrderAsc(screenId));
    }

    @Override
    public List<DpScreenSectionSummaryDto> findRootSectionsByScreen(Long screenId) {
        return mapper.toSummaryDtoList(
                sectionRepository.findAllByScreen_IdAndParentSectionIsNullAndIsDeletedFalse(screenId));
    }

    @Override
    public List<DpScreenSectionSummaryDto> findChildSections(Long parentSectionId) {
        return mapper.toSummaryDtoList(
                sectionRepository.findAllByParentSection_IdAndIsDeletedFalse(parentSectionId));
    }
}
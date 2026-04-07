package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.screenfield.*;
import com.meta_forge_platform.platform.application.mapper.DpScreenFieldMapper;
import com.meta_forge_platform.platform.application.service.DpScreenFieldService;
import com.meta_forge_platform.platform.domain.entity.DpScreenField;
import com.meta_forge_platform.platform.infrastructure.repository.DpFieldRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpScreenFieldRepository;
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

    @Override protected DpScreenFieldDto toDto(DpScreenField e) { return mapper.toDto(e); }
    @Override protected DpScreenField toEntity(CreateDpScreenFieldCmd c) { return mapper.toEntity(c); }
    @Override protected void updateEntity(DpScreenField e, UpdateDpScreenFieldCmd c) { mapper.updateEntity(e, c); }

    @Override
    protected void afterToEntity(DpScreenField entity, CreateDpScreenFieldCmd cmd) {
        entity.setScreen(screenRepository.findActiveById(cmd.getScreenId())
                .orElseThrow(() -> AppException.notFound("Screen", cmd.getScreenId())));
        entity.setField(fieldRepository.findActiveById(cmd.getFieldId())
                .orElseThrow(() -> AppException.notFound("Field", cmd.getFieldId())));
        if (cmd.getSectionId() != null) {
            entity.setSection(sectionRepository.findActiveById(cmd.getSectionId())
                    .orElseThrow(() -> AppException.notFound("Section", cmd.getSectionId())));
        }
    }

    @Override
    protected void beforeCreate(CreateDpScreenFieldCmd cmd) {
        if (screenFieldRepository.existsByScreen_IdAndField_IdAndSection_IdAndIsDeletedFalse(
                cmd.getScreenId(), cmd.getFieldId(), cmd.getSectionId()))
            throw AppException.conflict("Field đã tồn tại trong screen/section này");
    }

    @Override
    protected Specification<DpScreenField> buildKeywordSpec(String kw) {
        String p = "%" + kw.toLowerCase() + "%";
        return (root, q, cb) ->
                cb.like(cb.lower(root.get("displayLabel")), p);
    }

    @Override
    public List<DpScreenFieldSummaryDto> findAllByScreen(Long screenId) {
        return mapper.toSummaryDtoList(
                screenFieldRepository.findAllByScreen_IdAndIsDeletedFalseOrderBySortOrderAsc(screenId));
    }

    @Override
    public List<DpScreenFieldSummaryDto> findAllBySection(Long sectionId) {
        return mapper.toSummaryDtoList(
                screenFieldRepository.findAllBySection_IdAndIsDeletedFalseOrderBySortOrderAsc(sectionId));
    }
}
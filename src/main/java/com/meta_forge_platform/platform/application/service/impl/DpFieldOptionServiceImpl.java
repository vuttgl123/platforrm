package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.fieldoption.*;
import com.meta_forge_platform.platform.application.mapper.DpFieldOptionMapper;
import com.meta_forge_platform.platform.application.service.DpFieldOptionService;
import com.meta_forge_platform.platform.domain.entity.DpFieldOption;
import com.meta_forge_platform.platform.infrastructure.repository.DpFieldOptionRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpFieldRepository;
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
public class DpFieldOptionServiceImpl
        extends BaseServiceImpl<DpFieldOption, DpFieldOptionDto, CreateDpFieldOptionCmd, UpdateDpFieldOptionCmd, Long>
        implements DpFieldOptionService {

    private final DpFieldOptionRepository optionRepository;
    private final DpFieldRepository fieldRepository;
    private final DpFieldOptionMapper mapper;

    public DpFieldOptionServiceImpl(DpFieldOptionRepository optionRepository,
                                    DpFieldRepository fieldRepository,
                                    DpFieldOptionMapper mapper) {
        super(optionRepository);
        this.optionRepository = optionRepository;
        this.fieldRepository = fieldRepository;
        this.mapper = mapper;
    }

    @Override protected DpFieldOptionDto toDto(DpFieldOption e) { return mapper.toDto(e); }
    @Override protected DpFieldOption toEntity(CreateDpFieldOptionCmd c) { return mapper.toEntity(c); }
    @Override protected void updateEntity(DpFieldOption e, UpdateDpFieldOptionCmd c) { mapper.updateEntity(e, c); }

    @Override
    protected void afterToEntity(DpFieldOption entity, CreateDpFieldOptionCmd cmd) {
        entity.setField(fieldRepository.findActiveById(cmd.getFieldId())
                .orElseThrow(() -> AppException.notFound("Field", cmd.getFieldId())));
    }

    @Override
    protected void beforeCreate(CreateDpFieldOptionCmd cmd) {
        if (optionRepository.existsByField_IdAndOptionCodeAndIsDeletedFalse(
                cmd.getFieldId(), cmd.getOptionCode()))
            throw AppException.conflict("Option code đã tồn tại: " + cmd.getOptionCode());
    }

    @Override
    protected Specification<DpFieldOption> buildKeywordSpec(String kw) {
        String p = "%" + kw.toLowerCase() + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("optionCode")), p),
                cb.like(cb.lower(root.get("optionLabel")), p),
                cb.like(cb.lower(root.get("optionValue")), p)
        );
    }

    @Override
    public List<DpFieldOptionSummaryDto> findAllByField(Long fieldId) {
        return mapper.toSummaryDtoList(
                optionRepository.findAllByField_IdAndIsDeletedFalseOrderBySortOrderAsc(fieldId));
    }

    @Override
    public List<DpFieldOptionSummaryDto> findActiveByField(Long fieldId) {
        return mapper.toSummaryDtoList(
                optionRepository.findAllByField_IdAndIsActiveTrueAndIsDeletedFalseOrderBySortOrderAsc(fieldId));
    }
}
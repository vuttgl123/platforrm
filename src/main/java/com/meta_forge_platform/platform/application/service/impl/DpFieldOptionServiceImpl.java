package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.fieldoption.*;
import com.meta_forge_platform.platform.application.mapper.DpFieldOptionMapper;
import com.meta_forge_platform.platform.application.service.DpFieldOptionService;
import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.platform.domain.entity.DpFieldOption;
import com.meta_forge_platform.platform.infrastructure.repository.DpFieldOptionRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpFieldRepository;
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

    @Override
    protected String getEntityName() {
        return "DpFieldOption";
    }

    @Override
    protected DpFieldOptionDto toDto(DpFieldOption entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected DpFieldOption toEntity(CreateDpFieldOptionCmd command) {
        DpField field = fieldRepository.findActiveById(command.getFieldId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpField",
                        command.getFieldId()
                ));

        DpFieldOption option = DpFieldOption.create(
                field,
                command.getOptionCode(),
                command.getOptionLabel(),
                command.getOptionValue()
        );

        option.applyMetadata(
                command.getOptionLabel(),
                command.getOptionValue(),
                command.getColorCode(),
                command.getSortOrder(),
                command.getIsDefault(),
                command.getIsActive(),
                command.getConfigJson()
        );

        return option;
    }

    @Override
    protected void updateEntity(DpFieldOption entity, UpdateDpFieldOptionCmd command) {
        entity.applyMetadata(
                command.getOptionLabel(),
                command.getOptionValue(),
                command.getColorCode(),
                command.getSortOrder(),
                command.getIsDefault(),
                command.getIsActive(),
                command.getConfigJson()
        );
    }

    @Override
    protected void validateCreateCommand(CreateDpFieldOptionCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getFieldId(), "fieldId");
        validateNotNull(command.getOptionCode(), "optionCode");
        validateNotNull(command.getOptionLabel(), "optionLabel");
        validateNotNull(command.getOptionValue(), "optionValue");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getIsDefault(), "isDefault");
        validateNotNull(command.getIsActive(), "isActive");
    }

    @Override
    protected void validateUpdateCommand(UpdateDpFieldOptionCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getOptionLabel(), "optionLabel");
        validateNotNull(command.getOptionValue(), "optionValue");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getIsDefault(), "isDefault");
        validateNotNull(command.getIsActive(), "isActive");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateDpFieldOptionCmd command) {
        if (optionRepository.existsByField_IdAndOptionCodeAndIsDeletedFalse(
                command.getFieldId(),
                command.getOptionCode()
        )) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "DpFieldOption",
                    command.getOptionCode()
            );
        }

        if (Boolean.TRUE.equals(command.getIsDefault())) {
            optionRepository.clearAllDefaultByFieldId(command.getFieldId());
        }
    }

    @Override
    protected void beforeUpdate(DpFieldOption entity, UpdateDpFieldOptionCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "DpFieldOption",
                    entity.getId()
            );
        }

        if (Boolean.TRUE.equals(command.getIsDefault())) {
            optionRepository.clearDefaultByFieldIdExcept(entity.getField().getId(), entity.getId());
        }
    }

    @Override
    protected Specification<DpFieldOption> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("optionCode")), p),
                cb.like(cb.lower(root.get("optionLabel")), p),
                cb.like(cb.lower(root.get("optionValue")), p)
        );
    }

    @Override
    public List<DpFieldOptionSummaryDto> findAllByField(Long fieldId) {
        return mapper.toSummaryDtoList(
                optionRepository.findAllByField_IdAndIsDeletedFalseOrderBySortOrderAsc(fieldId)
        );
    }

    @Override
    public List<DpFieldOptionSummaryDto> findActiveByField(Long fieldId) {
        return mapper.toSummaryDtoList(
                optionRepository.findAllByField_IdAndIsActiveTrueAndIsDeletedFalseOrderBySortOrderAsc(fieldId)
        );
    }
}
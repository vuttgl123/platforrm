package com.meta_forge_platform.runtime.application.service.impl;

import com.meta_forge_platform.platform.infrastructure.repository.DpFieldRepository;
import com.meta_forge_platform.runtime.application.dto.recordvalue.*;
import com.meta_forge_platform.runtime.application.mapper.AppRecordValueMapper;
import com.meta_forge_platform.runtime.application.service.AppRecordValueService;
import com.meta_forge_platform.runtime.domain.entity.AppRecord;
import com.meta_forge_platform.runtime.domain.entity.AppRecordValue;
import com.meta_forge_platform.runtime.infrastructure.repository.AppRecordRepository;
import com.meta_forge_platform.runtime.infrastructure.repository.AppRecordValueRepository;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppRecordValueServiceImpl implements AppRecordValueService {

    private final AppRecordValueRepository valueRepository;
    private final AppRecordRepository recordRepository;
    private final DpFieldRepository fieldRepository;
    private final AppRecordValueMapper mapper;

    @Override
    public List<AppRecordValueSummaryDto> findByRecord(Long recordId) {
        return mapper.toSummaryDtoList(
                valueRepository.findAllByRecord_IdAndIsDeletedFalse(recordId)
        );
    }

    @Override
    public List<AppRecordValueSummaryDto> findByField(Long recordId, Long fieldId) {
        return mapper.toSummaryDtoList(
                valueRepository.findAllByRecord_IdAndField_IdAndIsDeletedFalse(recordId, fieldId)
        );
    }

    @Override
    public AppRecordValueDto upsert(CreateAppRecordValueCmd cmd) {

        AppRecord record = recordRepository.findActiveById(cmd.getRecordId())
                .orElseThrow(() -> AppException.of(ErrorCode.ENTITY_NOT_FOUND, "Record", cmd.getRecordId()));

        var field = fieldRepository.findActiveById(cmd.getFieldId())
                .orElseThrow(() -> AppException.of(ErrorCode.ENTITY_NOT_FOUND, "Field", cmd.getFieldId()));

        AppRecordValue value = valueRepository
                .findByRecord_IdAndField_IdAndSeqNoAndIsDeletedFalse(
                        cmd.getRecordId(), cmd.getFieldId(), cmd.getSeqNo())
                .orElseGet(() -> AppRecordValue.create(record, field, cmd.getSeqNo()));

        if (cmd.getValueString() != null) value.writeString(cmd.getValueString());
        else if (cmd.getValueText() != null) value.writeText(cmd.getValueText());
        else if (cmd.getValueInteger() != null) value.writeInteger(cmd.getValueInteger());
        else if (cmd.getValueJson() != null) value.writeJson(cmd.getValueJson());

        return mapper.toDto(valueRepository.save(value));
    }

    @Override
    public void deleteByRecord(Long recordId) {
        valueRepository.softDeleteByRecordId(recordId);
    }
}
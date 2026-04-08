package com.meta_forge_platform.runtime.application.service.impl;

import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.platform.infrastructure.repository.DpFieldRepository;
import com.meta_forge_platform.runtime.application.dto.recordblob.*;
import com.meta_forge_platform.runtime.application.mapper.AppRecordBlobMapper;
import com.meta_forge_platform.runtime.application.service.AppRecordBlobService;
import com.meta_forge_platform.runtime.domain.entity.AppBlob;
import com.meta_forge_platform.runtime.domain.entity.AppRecord;
import com.meta_forge_platform.runtime.domain.entity.AppRecordBlob;
import com.meta_forge_platform.runtime.infrastructure.repository.AppBlobRepository;
import com.meta_forge_platform.runtime.infrastructure.repository.AppRecordBlobRepository;
import com.meta_forge_platform.runtime.infrastructure.repository.AppRecordRepository;
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
public class AppRecordBlobServiceImpl
        extends BaseServiceImpl<AppRecordBlob, AppRecordBlobDto, CreateAppRecordBlobCmd, UpdateAppRecordBlobCmd, Long>
        implements AppRecordBlobService {

    private final AppRecordBlobRepository recordBlobRepository;
    private final AppRecordRepository recordRepository;
    private final DpFieldRepository fieldRepository;
    private final AppBlobRepository blobRepository;
    private final AppRecordBlobMapper mapper;

    public AppRecordBlobServiceImpl(AppRecordBlobRepository recordBlobRepository,
                                    AppRecordRepository recordRepository,
                                    DpFieldRepository fieldRepository,
                                    AppBlobRepository blobRepository,
                                    AppRecordBlobMapper mapper) {
        super(recordBlobRepository);
        this.recordBlobRepository = recordBlobRepository;
        this.recordRepository = recordRepository;
        this.fieldRepository = fieldRepository;
        this.blobRepository = blobRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getEntityName() {
        return "AppRecordBlob";
    }

    @Override
    protected AppRecordBlobDto toDto(AppRecordBlob entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected AppRecordBlob toEntity(CreateAppRecordBlobCmd command) {
        AppRecord record = recordRepository.findActiveById(command.getRecordId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "AppRecord",
                        command.getRecordId()
                ));

        DpField field = fieldRepository.findActiveById(command.getFieldId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpField",
                        command.getFieldId()
                ));

        AppBlob blob = blobRepository.findActiveById(command.getBlobId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "AppBlob",
                        command.getBlobId()
                ));

        AppRecordBlob entity = AppRecordBlob.create(record, field, blob);
        entity.applyMetadata(command.getSortOrder());
        return entity;
    }

    @Override
    protected void updateEntity(AppRecordBlob entity, UpdateAppRecordBlobCmd command) {
        entity.applyMetadata(command.getSortOrder());
    }

    @Override
    protected void validateCreateCommand(CreateAppRecordBlobCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getRecordId(), "recordId");
        validateNotNull(command.getFieldId(), "fieldId");
        validateNotNull(command.getBlobId(), "blobId");
        validateNotNull(command.getSortOrder(), "sortOrder");
    }

    @Override
    protected void validateUpdateCommand(UpdateAppRecordBlobCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateAppRecordBlobCmd command) {
        if (recordBlobRepository.existsByRecord_IdAndField_IdAndBlob_IdAndIsDeletedFalse(
                command.getRecordId(),
                command.getFieldId(),
                command.getBlobId()
        )) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "AppRecordBlob",
                    command.getBlobId()
            );
        }
    }

    @Override
    protected void beforeUpdate(AppRecordBlob entity, UpdateAppRecordBlobCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "AppRecordBlob",
                    entity.getId()
            );
        }
    }

    @Override
    protected Specification<AppRecordBlob> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.join("blob").get("fileName")), p),
                cb.like(cb.lower(root.join("field").get("fieldCode")), p)
        );
    }

    @Override
    public List<AppRecordBlobSummaryDto> findAllByRecord(Long recordId) {
        return mapper.toSummaryDtoList(
                recordBlobRepository.findAllByRecord_IdAndIsDeletedFalseOrderBySortOrderAsc(recordId)
        );
    }

    @Override
    public List<AppRecordBlobSummaryDto> findAllByRecordAndField(Long recordId, Long fieldId) {
        return mapper.toSummaryDtoList(
                recordBlobRepository.findAllByRecord_IdAndField_IdAndIsDeletedFalseOrderBySortOrderAsc(recordId, fieldId)
        );
    }

    @Override
    public AppRecordBlobDto getFirstByRecordAndField(Long recordId, Long fieldId) {
        return recordBlobRepository.findFirstByRecord_IdAndField_IdAndIsDeletedFalseOrderBySortOrderAsc(recordId, fieldId)
                .map(mapper::toDto)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "AppRecordBlob",
                        recordId + ":" + fieldId
                ));
    }
}
package com.meta_forge_platform.runtime.application.service.impl;

import com.meta_forge_platform.platform.domain.entity.DpEntityRelation;
import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRelationRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpFieldRepository;
import com.meta_forge_platform.runtime.application.dto.recordlink.*;
import com.meta_forge_platform.runtime.application.mapper.AppRecordLinkMapper;
import com.meta_forge_platform.runtime.application.service.AppRecordLinkService;
import com.meta_forge_platform.runtime.domain.entity.AppRecord;
import com.meta_forge_platform.runtime.domain.entity.AppRecordLink;
import com.meta_forge_platform.runtime.domain.enumeration.RecordLinkType;
import com.meta_forge_platform.runtime.infrastructure.repository.AppRecordLinkRepository;
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
public class AppRecordLinkServiceImpl
        extends BaseServiceImpl<AppRecordLink, AppRecordLinkDto, CreateAppRecordLinkCmd, UpdateAppRecordLinkCmd, Long>
        implements AppRecordLinkService {

    private final AppRecordLinkRepository recordLinkRepository;
    private final AppRecordRepository recordRepository;
    private final DpFieldRepository fieldRepository;
    private final DpEntityRelationRepository relationRepository;
    private final AppRecordLinkMapper mapper;

    public AppRecordLinkServiceImpl(AppRecordLinkRepository recordLinkRepository,
                                    AppRecordRepository recordRepository,
                                    DpFieldRepository fieldRepository,
                                    DpEntityRelationRepository relationRepository,
                                    AppRecordLinkMapper mapper) {
        super(recordLinkRepository);
        this.recordLinkRepository = recordLinkRepository;
        this.recordRepository = recordRepository;
        this.fieldRepository = fieldRepository;
        this.relationRepository = relationRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getEntityName() {
        return "AppRecordLink";
    }

    @Override
    protected AppRecordLinkDto toDto(AppRecordLink entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected AppRecordLink toEntity(CreateAppRecordLinkCmd command) {
        AppRecord sourceRecord = recordRepository.findActiveById(command.getSourceRecordId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "AppRecord",
                        command.getSourceRecordId()
                ));

        AppRecord targetRecord = recordRepository.findActiveById(command.getTargetRecordId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "AppRecord",
                        command.getTargetRecordId()
                ));

        DpEntityRelation relation = resolveRelation(command.getRelationId());
        DpField field = resolveField(command.getFieldId());

        AppRecordLink entity = AppRecordLink.create(
                sourceRecord,
                targetRecord,
                relation,
                field,
                toLinkType(command.getLinkType())
        );

        entity.applyMetadata(
                relation,
                field,
                toLinkType(command.getLinkType()),
                command.getSortOrder()
        );

        return entity;
    }

    @Override
    protected void updateEntity(AppRecordLink entity, UpdateAppRecordLinkCmd command) {
        entity.applyMetadata(
                resolveRelation(command.getRelationId()),
                resolveField(command.getFieldId()),
                toLinkType(command.getLinkType()),
                command.getSortOrder()
        );
    }

    @Override
    protected void validateCreateCommand(CreateAppRecordLinkCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getSourceRecordId(), "sourceRecordId");
        validateNotNull(command.getTargetRecordId(), "targetRecordId");
        validateNotNull(command.getLinkType(), "linkType");
        validateNotNull(command.getSortOrder(), "sortOrder");
    }

    @Override
    protected void validateUpdateCommand(UpdateAppRecordLinkCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getLinkType(), "linkType");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateAppRecordLinkCmd command) {
        if (command.getFieldId() != null
                && recordLinkRepository.existsBySourceRecord_IdAndField_IdAndTargetRecord_IdAndIsDeletedFalse(
                command.getSourceRecordId(),
                command.getFieldId(),
                command.getTargetRecordId()
        )) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "AppRecordLink",
                    command.getTargetRecordId()
            );
        }
    }

    @Override
    protected void beforeUpdate(AppRecordLink entity, UpdateAppRecordLinkCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "AppRecordLink",
                    entity.getId()
            );
        }
    }

    @Override
    protected Specification<AppRecordLink> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.join("sourceRecord").get("recordCode")), p),
                cb.like(cb.lower(root.join("targetRecord").get("recordCode")), p),
                cb.like(cb.lower(root.join("targetRecord").get("displayName")), p)
        );
    }

    @Override
    public List<AppRecordLinkSummaryDto> findAllBySourceRecord(Long sourceRecordId) {
        return mapper.toSummaryDtoList(
                recordLinkRepository.findAllBySourceRecord_IdAndIsDeletedFalseOrderBySortOrderAsc(sourceRecordId)
        );
    }

    @Override
    public List<AppRecordLinkSummaryDto> findAllBySourceRecordAndField(Long sourceRecordId, Long fieldId) {
        return mapper.toSummaryDtoList(
                recordLinkRepository.findAllBySourceRecord_IdAndField_IdAndIsDeletedFalseOrderBySortOrderAsc(sourceRecordId, fieldId)
        );
    }

    @Override
    public List<AppRecordLinkSummaryDto> findAllByTargetRecord(Long targetRecordId) {
        return mapper.toSummaryDtoList(
                recordLinkRepository.findAllByTargetRecord_IdAndIsDeletedFalseOrderBySortOrderAsc(targetRecordId)
        );
    }

    private DpEntityRelation resolveRelation(Long id) {
        if (id == null) {
            return null;
        }
        return relationRepository.findActiveById(id)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpEntityRelation",
                        id
                ));
    }

    private DpField resolveField(Long id) {
        if (id == null) {
            return null;
        }
        return fieldRepository.findActiveById(id)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpField",
                        id
                ));
    }

    private RecordLinkType toLinkType(String raw) {
        try {
            return RecordLinkType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "linkType", raw);
        }
    }
}
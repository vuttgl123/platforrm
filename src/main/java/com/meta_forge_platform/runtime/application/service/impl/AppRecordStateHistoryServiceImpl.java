package com.meta_forge_platform.runtime.application.service.impl;

import com.meta_forge_platform.runtime.application.dto.recordstatehistory.*;
import com.meta_forge_platform.runtime.application.mapper.AppRecordStateHistoryMapper;
import com.meta_forge_platform.runtime.application.service.AppRecordStateHistoryService;
import com.meta_forge_platform.runtime.domain.entity.AppRecordStateHistory;
import com.meta_forge_platform.runtime.infrastructure.repository.AppRecordStateHistoryRepository;
import com.meta_forge_platform.shared.application.BaseServiceImpl;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class AppRecordStateHistoryServiceImpl
        extends BaseServiceImpl<AppRecordStateHistory, AppRecordStateHistoryDto, Void, UpdateAppRecordStateHistoryCmd, Long>
        implements AppRecordStateHistoryService {

    private final AppRecordStateHistoryRepository historyRepository;
    private final AppRecordStateHistoryMapper mapper;

    public AppRecordStateHistoryServiceImpl(AppRecordStateHistoryRepository historyRepository,
                                            AppRecordStateHistoryMapper mapper) {
        super(historyRepository);
        this.historyRepository = historyRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getEntityName() {
        return "AppRecordStateHistory";
    }

    @Override
    protected AppRecordStateHistoryDto toDto(AppRecordStateHistory entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected AppRecordStateHistory toEntity(Void command) {
        throw AppException.of(ErrorCode.BAD_REQUEST, "historyCreationNotSupported");
    }

    @Override
    protected void updateEntity(AppRecordStateHistory entity, UpdateAppRecordStateHistoryCmd command) {
        entity.updateNote(command.getNote());
    }

    @Override
    protected void validateUpdateCommand(UpdateAppRecordStateHistoryCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeUpdate(AppRecordStateHistory entity, UpdateAppRecordStateHistoryCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "AppRecordStateHistory",
                    entity.getId()
            );
        }
    }

    @Override
    protected Specification<AppRecordStateHistory> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(cb.coalesce(root.get("actionCode"), "")), p),
                cb.like(cb.lower(cb.coalesce(root.get("note"), "")), p)
        );
    }

    @Override
    @Transactional
    public AppRecordStateHistoryDto create(Void command) {
        throw AppException.of(ErrorCode.BAD_REQUEST, "historyCreationNotSupported");
    }

    @Override
    public List<AppRecordStateHistorySummaryDto> findAllByRecord(Long recordId) {
        return mapper.toSummaryDtoList(
                historyRepository.findAllByRecord_IdAndIsDeletedFalseOrderByChangedAtDesc(recordId)
        );
    }

    @Override
    public AppRecordStateHistoryDto findLatestByRecord(Long recordId) {
        return historyRepository.findFirstByRecord_IdAndIsDeletedFalseOrderByChangedAtDesc(recordId)
                .map(mapper::toDto)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "AppRecordStateHistory",
                        recordId
                ));
    }
}
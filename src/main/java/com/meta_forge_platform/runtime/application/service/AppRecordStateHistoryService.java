package com.meta_forge_platform.runtime.application.service;

import com.meta_forge_platform.runtime.application.dto.recordstatehistory.*;
import com.meta_forge_platform.shared.application.BaseService;

import java.util.List;

public interface AppRecordStateHistoryService
        extends BaseService<AppRecordStateHistoryDto, Void, UpdateAppRecordStateHistoryCmd, Long> {

    List<AppRecordStateHistorySummaryDto> findAllByRecord(Long recordId);

    AppRecordStateHistoryDto findLatestByRecord(Long recordId);
}
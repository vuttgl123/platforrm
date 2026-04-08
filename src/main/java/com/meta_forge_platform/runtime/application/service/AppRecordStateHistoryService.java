package com.meta_forge_platform.runtime.application.service;

import com.meta_forge_platform.runtime.application.dto.recordstatehistory.AppRecordStateHistoryDto;
import com.meta_forge_platform.runtime.application.dto.recordstatehistory.AppRecordStateHistorySummaryDto;
import com.meta_forge_platform.runtime.application.dto.recordstatehistory.UpdateAppRecordStateHistoryCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface AppRecordStateHistoryService extends CrudService<
        AppRecordStateHistoryDto,
        Void,
        UpdateAppRecordStateHistoryCmd,
        Long> {

    List<AppRecordStateHistorySummaryDto> findAllByRecord(Long recordId);

    AppRecordStateHistoryDto findLatestByRecord(Long recordId);
}
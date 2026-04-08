package com.meta_forge_platform.runtime.application.service;

import com.meta_forge_platform.runtime.application.dto.record.*;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface AppRecordService extends CrudService<
        AppRecordDto,
        CreateAppRecordCmd,
        UpdateAppRecordCmd,
        Long> {

    AppRecordDto transition(Long recordId, TransitionAppRecordCmd command);

    List<AvailableTransitionDto> getAvailableTransitions(Long recordId);

    List<AppRecordSummaryDto> findChildren(Long parentRecordId);

    List<AppRecordSummaryDto> findByRootRecord(Long rootRecordId);
}
package com.meta_forge_platform.runtime.application.service;

import com.meta_forge_platform.runtime.application.dto.recordlink.*;
import com.meta_forge_platform.shared.application.BaseService;

import java.util.List;

public interface AppRecordLinkService
        extends BaseService<AppRecordLinkDto, CreateAppRecordLinkCmd, UpdateAppRecordLinkCmd, Long> {

    List<AppRecordLinkSummaryDto> findAllBySourceRecord(Long sourceRecordId);

    List<AppRecordLinkSummaryDto> findAllBySourceRecordAndField(Long sourceRecordId, Long fieldId);

    List<AppRecordLinkSummaryDto> findAllByTargetRecord(Long targetRecordId);
}
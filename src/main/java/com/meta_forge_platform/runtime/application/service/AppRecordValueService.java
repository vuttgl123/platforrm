package com.meta_forge_platform.runtime.application.service;

import com.meta_forge_platform.runtime.application.dto.recordvalue.AppRecordValueDto;
import com.meta_forge_platform.runtime.application.dto.recordvalue.AppRecordValueSummaryDto;
import com.meta_forge_platform.runtime.application.dto.recordvalue.CreateAppRecordValueCmd;

import java.util.List;

public interface AppRecordValueService {

    List<AppRecordValueSummaryDto> findByRecord(Long recordId);

    List<AppRecordValueSummaryDto> findByField(Long recordId, Long fieldId);

    AppRecordValueDto upsert(CreateAppRecordValueCmd cmd);

    void deleteByRecord(Long recordId);
}
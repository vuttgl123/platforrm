package com.meta_forge_platform.runtime.application.service;

import com.meta_forge_platform.runtime.application.dto.recordblob.*;
import com.meta_forge_platform.shared.application.BaseService;

import java.util.List;

public interface AppRecordBlobService
        extends BaseService<AppRecordBlobDto, CreateAppRecordBlobCmd, UpdateAppRecordBlobCmd, Long> {

    List<AppRecordBlobSummaryDto> findAllByRecord(Long recordId);

    List<AppRecordBlobSummaryDto> findAllByRecordAndField(Long recordId, Long fieldId);

    AppRecordBlobDto getFirstByRecordAndField(Long recordId, Long fieldId);
}
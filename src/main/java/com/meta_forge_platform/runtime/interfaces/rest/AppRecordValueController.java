package com.meta_forge_platform.runtime.interfaces.rest;

import com.meta_forge_platform.runtime.application.dto.recordvalue.AppRecordValueDto;
import com.meta_forge_platform.runtime.application.dto.recordvalue.AppRecordValueSummaryDto;
import com.meta_forge_platform.runtime.application.dto.recordvalue.CreateAppRecordValueCmd;
import com.meta_forge_platform.runtime.application.service.AppRecordValueService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/runtime/record-values")
public class AppRecordValueController {

    private final AppRecordValueService appRecordValueService;
    private final ApiResponseFactory responseFactory;

    public AppRecordValueController(AppRecordValueService appRecordValueService,
                                    ApiResponseFactory responseFactory) {
        this.appRecordValueService = appRecordValueService;
        this.responseFactory = responseFactory;
    }

    @GetMapping("/by-record/{recordId}")
    public ResponseEntity<ApiResponse<List<AppRecordValueSummaryDto>>> findByRecord(
            @PathVariable Long recordId
    ) {
        return responseFactory
                .success(appRecordValueService.findByRecord(recordId))
                .toResponseEntity();
    }

    @GetMapping("/by-record/{recordId}/field/{fieldId}")
    public ResponseEntity<ApiResponse<List<AppRecordValueSummaryDto>>> findByField(
            @PathVariable Long recordId,
            @PathVariable Long fieldId
    ) {
        return responseFactory
                .success(appRecordValueService.findByField(recordId, fieldId))
                .toResponseEntity();
    }

    @PostMapping("/upsert")
    public ResponseEntity<ApiResponse<AppRecordValueDto>> upsert(
            @RequestBody CreateAppRecordValueCmd cmd
    ) {
        return responseFactory
                .success(appRecordValueService.upsert(cmd))
                .toResponseEntity();
    }

    @DeleteMapping("/by-record/{recordId}")
    public ResponseEntity<ApiResponse<Void>> deleteByRecord(
            @PathVariable Long recordId
    ) {
        appRecordValueService.deleteByRecord(recordId);
        return responseFactory
                .<Void>success(null)
                .toResponseEntity();
    }
}
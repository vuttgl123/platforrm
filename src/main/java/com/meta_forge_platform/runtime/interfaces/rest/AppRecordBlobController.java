package com.meta_forge_platform.runtime.interfaces.rest;

import com.meta_forge_platform.runtime.application.dto.recordblob.*;
import com.meta_forge_platform.runtime.application.service.AppRecordBlobService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/runtime/record-blobs")
public class AppRecordBlobController extends BaseCrudController<
        AppRecordBlobDto,
        CreateAppRecordBlobCmd,
        UpdateAppRecordBlobCmd,
        Long> {

    private final AppRecordBlobService appRecordBlobService;

    public AppRecordBlobController(AppRecordBlobService appRecordBlobService,
                                   ApiResponseFactory responseFactory) {
        super(appRecordBlobService, responseFactory);
        this.appRecordBlobService = appRecordBlobService;
    }

    @GetMapping("/by-record/{recordId}")
    public ResponseEntity<ApiResponse<List<AppRecordBlobSummaryDto>>> findAllByRecord(
            @PathVariable Long recordId
    ) {
        return responseFactory
                .success(appRecordBlobService.findAllByRecord(recordId))
                .toResponseEntity();
    }

    @GetMapping("/by-record/{recordId}/field/{fieldId}")
    public ResponseEntity<ApiResponse<List<AppRecordBlobSummaryDto>>> findAllByRecordAndField(
            @PathVariable Long recordId,
            @PathVariable Long fieldId
    ) {
        return responseFactory
                .success(appRecordBlobService.findAllByRecordAndField(recordId, fieldId))
                .toResponseEntity();
    }

    @GetMapping("/first")
    public ResponseEntity<ApiResponse<AppRecordBlobDto>> getFirstByRecordAndField(
            @RequestParam Long recordId,
            @RequestParam Long fieldId
    ) {
        return responseFactory
                .success(appRecordBlobService.getFirstByRecordAndField(recordId, fieldId))
                .toResponseEntity();
    }
}
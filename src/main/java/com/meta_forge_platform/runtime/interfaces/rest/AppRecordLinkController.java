package com.meta_forge_platform.runtime.interfaces.rest;

import com.meta_forge_platform.runtime.application.dto.recordlink.*;
import com.meta_forge_platform.runtime.application.service.AppRecordLinkService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/runtime/record-links")
public class AppRecordLinkController extends BaseCrudController<
        AppRecordLinkDto,
        CreateAppRecordLinkCmd,
        UpdateAppRecordLinkCmd,
        Long> {

    private final AppRecordLinkService appRecordLinkService;

    public AppRecordLinkController(AppRecordLinkService appRecordLinkService,
                                   ApiResponseFactory responseFactory) {
        super(appRecordLinkService, responseFactory);
        this.appRecordLinkService = appRecordLinkService;
    }

    @GetMapping("/by-source/{sourceRecordId}")
    public ResponseEntity<ApiResponse<List<AppRecordLinkSummaryDto>>> findAllBySourceRecord(
            @PathVariable Long sourceRecordId
    ) {
        return responseFactory
                .success(appRecordLinkService.findAllBySourceRecord(sourceRecordId))
                .toResponseEntity();
    }

    @GetMapping("/by-source/{sourceRecordId}/field/{fieldId}")
    public ResponseEntity<ApiResponse<List<AppRecordLinkSummaryDto>>> findAllBySourceRecordAndField(
            @PathVariable Long sourceRecordId,
            @PathVariable Long fieldId
    ) {
        return responseFactory
                .success(appRecordLinkService.findAllBySourceRecordAndField(sourceRecordId, fieldId))
                .toResponseEntity();
    }

    @GetMapping("/by-target/{targetRecordId}")
    public ResponseEntity<ApiResponse<List<AppRecordLinkSummaryDto>>> findAllByTargetRecord(
            @PathVariable Long targetRecordId
    ) {
        return responseFactory
                .success(appRecordLinkService.findAllByTargetRecord(targetRecordId))
                .toResponseEntity();
    }
}
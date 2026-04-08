package com.meta_forge_platform.runtime.interfaces.rest;

import com.meta_forge_platform.runtime.application.dto.record.*;
import com.meta_forge_platform.runtime.application.service.AppRecordService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/runtime/records")
public class AppRecordController extends BaseCrudController<
        AppRecordDto,
        CreateAppRecordCmd,
        UpdateAppRecordCmd,
        Long> {

    private final AppRecordService appRecordService;

    public AppRecordController(AppRecordService appRecordService,
                               ApiResponseFactory responseFactory) {
        super(appRecordService, responseFactory);
        this.appRecordService = appRecordService;
    }

    // =========================
    // Workflow
    // =========================

    @PostMapping("/{id}/transition")
    public ResponseEntity<ApiResponse<AppRecordDto>> transition(
            @PathVariable Long id,
            @RequestBody TransitionAppRecordCmd command
    ) {
        return responseFactory
                .success(appRecordService.transition(id, command))
                .toResponseEntity();
    }

    @GetMapping("/{id}/available-transitions")
    public ResponseEntity<ApiResponse<List<AvailableTransitionDto>>> getAvailableTransitions(
            @PathVariable Long id
    ) {
        return responseFactory
                .success(appRecordService.getAvailableTransitions(id))
                .toResponseEntity();
    }

    // =========================
    // Hierarchy
    // =========================

    @GetMapping("/{id}/children")
    public ResponseEntity<ApiResponse<List<AppRecordSummaryDto>>> findChildren(
            @PathVariable Long id
    ) {
        return responseFactory
                .success(appRecordService.findChildren(id))
                .toResponseEntity();
    }

    @GetMapping("/root/{rootId}")
    public ResponseEntity<ApiResponse<List<AppRecordSummaryDto>>> findByRoot(
            @PathVariable Long rootId
    ) {
        return responseFactory
                .success(appRecordService.findByRootRecord(rootId))
                .toResponseEntity();
    }
}
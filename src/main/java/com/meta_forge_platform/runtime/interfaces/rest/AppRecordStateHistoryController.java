package com.meta_forge_platform.runtime.interfaces.rest;

import com.meta_forge_platform.runtime.application.dto.recordstatehistory.AppRecordStateHistoryDto;
import com.meta_forge_platform.runtime.application.dto.recordstatehistory.AppRecordStateHistorySummaryDto;
import com.meta_forge_platform.runtime.application.dto.recordstatehistory.UpdateAppRecordStateHistoryCmd;
import com.meta_forge_platform.runtime.application.service.AppRecordStateHistoryService;
import com.meta_forge_platform.shared.domain.query.PageQuery;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.PageMeta;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/runtime/record-state-histories")
public class AppRecordStateHistoryController {

    private final AppRecordStateHistoryService appRecordStateHistoryService;
    private final ApiResponseFactory responseFactory;

    public AppRecordStateHistoryController(AppRecordStateHistoryService appRecordStateHistoryService,
                                           ApiResponseFactory responseFactory) {
        this.appRecordStateHistoryService = appRecordStateHistoryService;
        this.responseFactory = responseFactory;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppRecordStateHistoryDto>>> findAll(
            @ModelAttribute PageQuery query
    ) {
        Page<AppRecordStateHistoryDto> page = appRecordStateHistoryService.findAll(query);
        return responseFactory
                .success(page.getContent(), PageMeta.from(page))
                .toResponseEntity();
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AppRecordStateHistoryDto>>> findAll() {
        return responseFactory
                .success(appRecordStateHistoryService.findAll())
                .toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppRecordStateHistoryDto>> getById(
            @PathVariable Long id
    ) {
        return responseFactory
                .success(appRecordStateHistoryService.getById(id))
                .toResponseEntity();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppRecordStateHistoryDto>> update(
            @PathVariable Long id,
            @RequestBody UpdateAppRecordStateHistoryCmd command
    ) {
        return responseFactory
                .success(appRecordStateHistoryService.update(id, command))
                .toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id) {
        appRecordStateHistoryService.deleteById(id);
        return responseFactory
                .<Void>success(null)
                .toResponseEntity();
    }

    @PostMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<AppRecordStateHistoryDto>> restore(
            @PathVariable Long id
    ) {
        return responseFactory
                .success(appRecordStateHistoryService.restore(id))
                .toResponseEntity();
    }

    @GetMapping("/by-record/{recordId}")
    public ResponseEntity<ApiResponse<List<AppRecordStateHistorySummaryDto>>> findAllByRecord(
            @PathVariable Long recordId
    ) {
        return responseFactory
                .success(appRecordStateHistoryService.findAllByRecord(recordId))
                .toResponseEntity();
    }

    @GetMapping("/latest/{recordId}")
    public ResponseEntity<ApiResponse<AppRecordStateHistoryDto>> findLatestByRecord(
            @PathVariable Long recordId
    ) {
        return responseFactory
                .success(appRecordStateHistoryService.findLatestByRecord(recordId))
                .toResponseEntity();
    }
}
package com.meta_forge_platform.platform.interfaces.rest;

import com.meta_forge_platform.platform.application.dto.entity.CreateDpEntityCmd;
import com.meta_forge_platform.platform.application.dto.entity.DpEntityDto;
import com.meta_forge_platform.platform.application.dto.entity.DpEntitySummaryDto;
import com.meta_forge_platform.platform.application.dto.entity.UpdateDpEntityCmd;
import com.meta_forge_platform.platform.application.service.DpEntityService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/entities")
public class DpEntityController extends BaseCrudController<
        DpEntityDto,
        CreateDpEntityCmd,
        UpdateDpEntityCmd,
        Long> {

    private final DpEntityService dpEntityService;

    public DpEntityController(DpEntityService dpEntityService,
                              ApiResponseFactory responseFactory) {
        super(dpEntityService, responseFactory);
        this.dpEntityService = dpEntityService;
    }

    @GetMapping("/by-code")
    public ResponseEntity<ApiResponse<DpEntityDto>> getByCode(
            @RequestParam Long moduleId,
            @RequestParam String entityCode
    ) {
        return responseFactory
                .success(dpEntityService.getByCode(moduleId, entityCode))
                .toResponseEntity();
    }

    @GetMapping("/by-module/{moduleId}")
    public ResponseEntity<ApiResponse<List<DpEntitySummaryDto>>> findAllByModule(
            @PathVariable Long moduleId
    ) {
        return responseFactory
                .success(dpEntityService.findAllByModule(moduleId))
                .toResponseEntity();
    }

    @GetMapping("/by-module/{moduleId}/root")
    public ResponseEntity<ApiResponse<List<DpEntitySummaryDto>>> findRootEntitiesByModule(
            @PathVariable Long moduleId
    ) {
        return responseFactory
                .success(dpEntityService.findRootEntitiesByModule(moduleId))
                .toResponseEntity();
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<List<DpEntitySummaryDto>>> findAllSummary() {
        return responseFactory
                .success(dpEntityService.findAllSummary())
                .toResponseEntity();
    }
}
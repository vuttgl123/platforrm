package com.meta_forge_platform.platform.interfaces.rest;

import com.meta_forge_platform.platform.application.dto.entityrelation.CreateDpEntityRelationCmd;
import com.meta_forge_platform.platform.application.dto.entityrelation.DpEntityRelationDto;
import com.meta_forge_platform.platform.application.dto.entityrelation.DpEntityRelationSummaryDto;
import com.meta_forge_platform.platform.application.dto.entityrelation.UpdateDpEntityRelationCmd;
import com.meta_forge_platform.platform.application.service.DpEntityRelationService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/entity-relations")
public class DpEntityRelationController extends BaseCrudController<
        DpEntityRelationDto,
        CreateDpEntityRelationCmd,
        UpdateDpEntityRelationCmd,
        Long> {

    private final DpEntityRelationService dpEntityRelationService;

    public DpEntityRelationController(DpEntityRelationService dpEntityRelationService,
                                      ApiResponseFactory responseFactory) {
        super(dpEntityRelationService, responseFactory);
        this.dpEntityRelationService = dpEntityRelationService;
    }

    @GetMapping("/by-source-entity/{sourceEntityId}")
    public ResponseEntity<ApiResponse<List<DpEntityRelationSummaryDto>>> findAllBySourceEntity(
            @PathVariable Long sourceEntityId
    ) {
        return responseFactory
                .success(dpEntityRelationService.findAllBySourceEntity(sourceEntityId))
                .toResponseEntity();
    }

    @GetMapping("/by-target-entity/{targetEntityId}")
    public ResponseEntity<ApiResponse<List<DpEntityRelationSummaryDto>>> findAllByTargetEntity(
            @PathVariable Long targetEntityId
    ) {
        return responseFactory
                .success(dpEntityRelationService.findAllByTargetEntity(targetEntityId))
                .toResponseEntity();
    }
}
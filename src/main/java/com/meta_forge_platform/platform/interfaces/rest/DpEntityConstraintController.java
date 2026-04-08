package com.meta_forge_platform.platform.interfaces.rest;

import com.meta_forge_platform.platform.application.dto.entityconstraint.CreateDpEntityConstraintCmd;
import com.meta_forge_platform.platform.application.dto.entityconstraint.DpEntityConstraintDto;
import com.meta_forge_platform.platform.application.dto.entityconstraint.DpEntityConstraintSummaryDto;
import com.meta_forge_platform.platform.application.dto.entityconstraint.UpdateDpEntityConstraintCmd;
import com.meta_forge_platform.platform.application.service.DpEntityConstraintService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/entity-constraints")
public class DpEntityConstraintController extends BaseCrudController<
        DpEntityConstraintDto,
        CreateDpEntityConstraintCmd,
        UpdateDpEntityConstraintCmd,
        Long> {

    private final DpEntityConstraintService dpEntityConstraintService;

    public DpEntityConstraintController(DpEntityConstraintService dpEntityConstraintService,
                                        ApiResponseFactory responseFactory) {
        super(dpEntityConstraintService, responseFactory);
        this.dpEntityConstraintService = dpEntityConstraintService;
    }

    @GetMapping("/by-entity/{entityId}")
    public ResponseEntity<ApiResponse<List<DpEntityConstraintSummaryDto>>> findAllByEntity(
            @PathVariable Long entityId
    ) {
        return responseFactory
                .success(dpEntityConstraintService.findAllByEntity(entityId))
                .toResponseEntity();
    }

    @GetMapping("/by-entity/{entityId}/active")
    public ResponseEntity<ApiResponse<List<DpEntityConstraintSummaryDto>>> findActiveByEntity(
            @PathVariable Long entityId
    ) {
        return responseFactory
                .success(dpEntityConstraintService.findActiveByEntity(entityId))
                .toResponseEntity();
    }
}
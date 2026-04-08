package com.meta_forge_platform.platform.interfaces.rest;

import com.meta_forge_platform.platform.application.dto.workflow.CreateDpWorkflowCmd;
import com.meta_forge_platform.platform.application.dto.workflow.DpWorkflowDto;
import com.meta_forge_platform.platform.application.dto.workflow.DpWorkflowSummaryDto;
import com.meta_forge_platform.platform.application.dto.workflow.UpdateDpWorkflowCmd;
import com.meta_forge_platform.platform.application.service.DpWorkflowService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/workflows")
public class DpWorkflowController extends BaseCrudController<
        DpWorkflowDto,
        CreateDpWorkflowCmd,
        UpdateDpWorkflowCmd,
        Long> {

    private final DpWorkflowService dpWorkflowService;

    public DpWorkflowController(DpWorkflowService dpWorkflowService,
                                ApiResponseFactory responseFactory) {
        super(dpWorkflowService, responseFactory);
        this.dpWorkflowService = dpWorkflowService;
    }

    @GetMapping("/by-entity/{entityId}")
    public ResponseEntity<ApiResponse<List<DpWorkflowSummaryDto>>> findAllByEntity(
            @PathVariable Long entityId
    ) {
        return responseFactory
                .success(dpWorkflowService.findAllByEntity(entityId))
                .toResponseEntity();
    }

    @GetMapping("/default/{entityId}")
    public ResponseEntity<ApiResponse<DpWorkflowDto>> getDefaultByEntity(
            @PathVariable Long entityId
    ) {
        return responseFactory
                .success(dpWorkflowService.getDefaultByEntity(entityId))
                .toResponseEntity();
    }
}
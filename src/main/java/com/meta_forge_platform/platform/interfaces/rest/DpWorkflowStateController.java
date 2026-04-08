package com.meta_forge_platform.platform.interfaces.rest;

import com.meta_forge_platform.platform.application.dto.workflowstate.CreateDpWorkflowStateCmd;
import com.meta_forge_platform.platform.application.dto.workflowstate.DpWorkflowStateDto;
import com.meta_forge_platform.platform.application.dto.workflowstate.DpWorkflowStateSummaryDto;
import com.meta_forge_platform.platform.application.dto.workflowstate.UpdateDpWorkflowStateCmd;
import com.meta_forge_platform.platform.application.service.DpWorkflowStateService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/workflow-states")
public class DpWorkflowStateController extends BaseCrudController<
        DpWorkflowStateDto,
        CreateDpWorkflowStateCmd,
        UpdateDpWorkflowStateCmd,
        Long> {

    private final DpWorkflowStateService dpWorkflowStateService;

    public DpWorkflowStateController(DpWorkflowStateService dpWorkflowStateService,
                                     ApiResponseFactory responseFactory) {
        super(dpWorkflowStateService, responseFactory);
        this.dpWorkflowStateService = dpWorkflowStateService;
    }

    @GetMapping("/by-workflow/{workflowId}")
    public ResponseEntity<ApiResponse<List<DpWorkflowStateSummaryDto>>> findAllByWorkflow(
            @PathVariable Long workflowId
    ) {
        return responseFactory
                .success(dpWorkflowStateService.findAllByWorkflow(workflowId))
                .toResponseEntity();
    }

    @GetMapping("/initial/{workflowId}")
    public ResponseEntity<ApiResponse<DpWorkflowStateSummaryDto>> getInitialState(
            @PathVariable Long workflowId
    ) {
        return responseFactory
                .success(dpWorkflowStateService.getInitialState(workflowId))
                .toResponseEntity();
    }
}
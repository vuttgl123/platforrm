package com.meta_forge_platform.platform.interfaces.rest;

import com.meta_forge_platform.platform.application.dto.workflowtransition.CreateDpWorkflowTransitionCmd;
import com.meta_forge_platform.platform.application.dto.workflowtransition.DpWorkflowTransitionDto;
import com.meta_forge_platform.platform.application.dto.workflowtransition.DpWorkflowTransitionSummaryDto;
import com.meta_forge_platform.platform.application.dto.workflowtransition.UpdateDpWorkflowTransitionCmd;
import com.meta_forge_platform.platform.application.service.DpWorkflowTransitionService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/workflow-transitions")
public class DpWorkflowTransitionController extends BaseCrudController<
        DpWorkflowTransitionDto,
        CreateDpWorkflowTransitionCmd,
        UpdateDpWorkflowTransitionCmd,
        Long> {

    private final DpWorkflowTransitionService dpWorkflowTransitionService;

    public DpWorkflowTransitionController(DpWorkflowTransitionService dpWorkflowTransitionService,
                                          ApiResponseFactory responseFactory) {
        super(dpWorkflowTransitionService, responseFactory);
        this.dpWorkflowTransitionService = dpWorkflowTransitionService;
    }

    @GetMapping("/by-workflow/{workflowId}")
    public ResponseEntity<ApiResponse<List<DpWorkflowTransitionSummaryDto>>> findAllByWorkflow(
            @PathVariable Long workflowId
    ) {
        return responseFactory
                .success(dpWorkflowTransitionService.findAllByWorkflow(workflowId))
                .toResponseEntity();
    }

    @GetMapping("/available/{fromStateId}")
    public ResponseEntity<ApiResponse<List<DpWorkflowTransitionSummaryDto>>> findAvailableTransitions(
            @PathVariable Long fromStateId
    ) {
        return responseFactory
                .success(dpWorkflowTransitionService.findAvailableTransitions(fromStateId))
                .toResponseEntity();
    }
}
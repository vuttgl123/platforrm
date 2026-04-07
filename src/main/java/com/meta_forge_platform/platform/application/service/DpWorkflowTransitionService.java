package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.workflowtransition.*;
import com.meta_forge_platform.shared.application.BaseService;
import java.util.List;

public interface DpWorkflowTransitionService
        extends BaseService<DpWorkflowTransitionDto, CreateDpWorkflowTransitionCmd, UpdateDpWorkflowTransitionCmd, Long> {

    List<DpWorkflowTransitionSummaryDto> findAllByWorkflow(Long workflowId);
    List<DpWorkflowTransitionSummaryDto> findAvailableTransitions(Long fromStateId);
}
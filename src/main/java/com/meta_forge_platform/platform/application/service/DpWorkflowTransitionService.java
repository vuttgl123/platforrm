package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.workflowtransition.CreateDpWorkflowTransitionCmd;
import com.meta_forge_platform.platform.application.dto.workflowtransition.DpWorkflowTransitionDto;
import com.meta_forge_platform.platform.application.dto.workflowtransition.DpWorkflowTransitionSummaryDto;
import com.meta_forge_platform.platform.application.dto.workflowtransition.UpdateDpWorkflowTransitionCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface DpWorkflowTransitionService extends CrudService<
        DpWorkflowTransitionDto,
        CreateDpWorkflowTransitionCmd,
        UpdateDpWorkflowTransitionCmd,
        Long> {

    List<DpWorkflowTransitionSummaryDto> findAllByWorkflow(Long workflowId);

    List<DpWorkflowTransitionSummaryDto> findAvailableTransitions(Long fromStateId);
}
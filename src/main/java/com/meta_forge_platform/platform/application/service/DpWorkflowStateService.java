package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.workflowstate.CreateDpWorkflowStateCmd;
import com.meta_forge_platform.platform.application.dto.workflowstate.DpWorkflowStateDto;
import com.meta_forge_platform.platform.application.dto.workflowstate.DpWorkflowStateSummaryDto;
import com.meta_forge_platform.platform.application.dto.workflowstate.UpdateDpWorkflowStateCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface DpWorkflowStateService extends CrudService<
        DpWorkflowStateDto,
        CreateDpWorkflowStateCmd,
        UpdateDpWorkflowStateCmd,
        Long> {

    List<DpWorkflowStateSummaryDto> findAllByWorkflow(Long workflowId);

    DpWorkflowStateSummaryDto getInitialState(Long workflowId);
}
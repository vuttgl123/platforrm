package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.workflowstate.*;
import com.meta_forge_platform.shared.application.BaseService;
import java.util.List;

public interface DpWorkflowStateService
        extends BaseService<DpWorkflowStateDto, CreateDpWorkflowStateCmd, UpdateDpWorkflowStateCmd, Long> {

    List<DpWorkflowStateSummaryDto> findAllByWorkflow(Long workflowId);
    DpWorkflowStateSummaryDto getInitialState(Long workflowId);
}
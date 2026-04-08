package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.workflow.CreateDpWorkflowCmd;
import com.meta_forge_platform.platform.application.dto.workflow.DpWorkflowDto;
import com.meta_forge_platform.platform.application.dto.workflow.DpWorkflowSummaryDto;
import com.meta_forge_platform.platform.application.dto.workflow.UpdateDpWorkflowCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface DpWorkflowService extends CrudService<
        DpWorkflowDto,
        CreateDpWorkflowCmd,
        UpdateDpWorkflowCmd,
        Long> {

    List<DpWorkflowSummaryDto> findAllByEntity(Long entityId);

    DpWorkflowDto getDefaultByEntity(Long entityId);
}
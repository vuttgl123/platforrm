package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.workflow.*;
import com.meta_forge_platform.shared.application.BaseService;
import java.util.List;

public interface DpWorkflowService
        extends BaseService<DpWorkflowDto, CreateDpWorkflowCmd, UpdateDpWorkflowCmd, Long> {

    List<DpWorkflowSummaryDto> findAllByEntity(Long entityId);
    DpWorkflowDto getDefaultByEntity(Long entityId);
}
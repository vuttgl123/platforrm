package com.meta_forge_platform.platform.application.dto.workflow;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpWorkflowSummaryDto {
    private Long id;
    private String workflowCode;
    private String workflowName;
    private Boolean isDefault;
    private Boolean isActive;
    private Long entityId;
}
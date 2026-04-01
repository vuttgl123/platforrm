package com.meta_forge_platform.platform.application.dto.workflowstate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpWorkflowStateSummaryDto {
    private Long id;
    private String stateCode;
    private String stateName;
    private String stateType;
    private Boolean isInitial;
    private Boolean isFinal;
    private String colorCode;
    private Integer sortOrder;
    private Long workflowId;
}
package com.meta_forge_platform.platform.application.dto.workflowtransition;

import com.meta_forge_platform.platform.application.dto.workflow.DpWorkflowSummaryDto;
import com.meta_forge_platform.platform.application.dto.workflowstate.DpWorkflowStateSummaryDto;
import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class DpWorkflowTransitionDto extends BaseDto {
    private DpWorkflowSummaryDto workflow;
    private String transitionCode;
    private String transitionName;
    private DpWorkflowStateSummaryDto fromState;
    private DpWorkflowStateSummaryDto toState;
    private String actionCode;
    private Map<String, Object> conditionJson;
    private Map<String, Object> effectJson;
    private Boolean isActive;
    private Integer sortOrder;
    private Boolean isDeleted;
}
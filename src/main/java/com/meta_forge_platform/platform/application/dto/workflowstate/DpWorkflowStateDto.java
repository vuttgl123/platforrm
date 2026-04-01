package com.meta_forge_platform.platform.application.dto.workflowstate;

import com.meta_forge_platform.platform.application.dto.workflow.DpWorkflowSummaryDto;
import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class DpWorkflowStateDto extends BaseDto {
    private DpWorkflowSummaryDto workflow;
    private String stateCode;
    private String stateName;
    private String stateType;
    private Boolean isInitial;
    private Boolean isFinal;
    private String colorCode;
    private Integer sortOrder;
    private Map<String, Object> configJson;
    private Boolean isDeleted;
}
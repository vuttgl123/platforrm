package com.meta_forge_platform.platform.application.dto.workflow;

import com.meta_forge_platform.platform.application.dto.entity.DpEntitySummaryDto;
import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class DpWorkflowDto extends BaseDto {
    private DpEntitySummaryDto entity;
    private String workflowCode;
    private String workflowName;
    private String description;
    private Boolean isDefault;
    private Boolean isActive;
    private Map<String, Object> configJson;
    private Boolean isDeleted;
}
package com.meta_forge_platform.runtime.application.dto.record;

import com.meta_forge_platform.platform.application.dto.entity.DpEntitySummaryDto;
import com.meta_forge_platform.platform.application.dto.workflowstate.DpWorkflowStateSummaryDto;
import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class AppRecordDto extends BaseDto {
    private DpEntitySummaryDto entity;
    private String recordCode;
    private String displayName;
    private DpWorkflowStateSummaryDto currentState;
    private Long parentRecordId;
    private String parentDisplayName;
    private Long rootRecordId;
    private String status;
    private Map<String, Object> dataJson;
    private Boolean isDeleted;
}
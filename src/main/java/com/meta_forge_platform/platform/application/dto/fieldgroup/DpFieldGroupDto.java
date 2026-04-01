package com.meta_forge_platform.platform.application.dto.fieldgroup;

import com.meta_forge_platform.platform.application.dto.entity.DpEntitySummaryDto;
import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class DpFieldGroupDto extends BaseDto {
    private DpEntitySummaryDto entity;
    private String groupCode;
    private String groupName;
    private Integer sortOrder;
    private Map<String, Object> configJson;
    private Boolean isDeleted;
}
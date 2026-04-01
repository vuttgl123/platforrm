package com.meta_forge_platform.platform.application.dto.view;

import com.meta_forge_platform.platform.application.dto.entity.DpEntitySummaryDto;
import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class DpViewDto extends BaseDto {
    private DpEntitySummaryDto entity;
    private String viewCode;
    private String viewName;
    private String viewType;
    private Map<String, Object> queryJson;
    private List<Object> columnsJson;
    private Boolean isDefault;
    private Boolean isActive;
    private Boolean isDeleted;
}
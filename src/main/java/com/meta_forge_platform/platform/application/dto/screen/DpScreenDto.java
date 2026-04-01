package com.meta_forge_platform.platform.application.dto.screen;

import com.meta_forge_platform.platform.application.dto.entity.DpEntitySummaryDto;
import com.meta_forge_platform.platform.application.dto.module.DpModuleSummaryDto;
import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class DpScreenDto extends BaseDto {
    private DpModuleSummaryDto module;
    private DpEntitySummaryDto entity;
    private String screenCode;
    private String screenName;
    private String screenType;
    private String routePath;
    private Boolean isActive;
    private Integer sortOrder;
    private Map<String, Object> configJson;
    private Boolean isDeleted;
}
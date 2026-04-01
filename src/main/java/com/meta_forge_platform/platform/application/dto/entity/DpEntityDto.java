package com.meta_forge_platform.platform.application.dto.entity;

import com.meta_forge_platform.platform.application.dto.module.DpModuleSummaryDto;
import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class DpEntityDto extends BaseDto {
    private DpModuleSummaryDto module;
    private String entityCode;
    private String entityName;
    private String tableStrategy;
    private String description;
    private Boolean isRoot;
    private Boolean isActive;
    private Boolean isDeleted;
    private String displayNamePattern;
    private List<Object> defaultSortJson;
    private Map<String, Object> configJson;
}
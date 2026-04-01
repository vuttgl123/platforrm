package com.meta_forge_platform.platform.application.dto.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpEntitySummaryDto {
    private Long id;
    private String entityCode;
    private String entityName;
    private String tableStrategy;
    private Boolean isRoot;
    private Boolean isActive;
    private Long moduleId;
    private String moduleName;
}
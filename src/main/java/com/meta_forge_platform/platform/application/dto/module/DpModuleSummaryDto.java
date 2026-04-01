package com.meta_forge_platform.platform.application.dto.module;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpModuleSummaryDto {
    private Long id;
    private String moduleCode;
    private String moduleName;
    private String status;
    private Integer sortOrder;
    private Boolean isSystem;
}
package com.meta_forge_platform.platform.application.dto.view;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpViewSummaryDto {
    private Long id;
    private String viewCode;
    private String viewName;
    private String viewType;
    private Boolean isDefault;
    private Boolean isActive;
    private Long entityId;
}
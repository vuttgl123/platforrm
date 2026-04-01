package com.meta_forge_platform.platform.application.dto.screen;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpScreenSummaryDto {
    private Long id;
    private String screenCode;
    private String screenName;
    private String screenType;
    private String routePath;
    private Boolean isActive;
    private Integer sortOrder;
    private Long moduleId;
    private Long entityId;
}
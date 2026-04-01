package com.meta_forge_platform.platform.application.dto.menu;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpMenuSummaryDto {
    private Long id;
    private String menuCode;
    private String menuName;
    private String menuType;
    private String routePath;
    private String icon;
    private Integer sortOrder;
    private Boolean isActive;
    private Long moduleId;
    private Long parentMenuId;
    private Long screenId;
}
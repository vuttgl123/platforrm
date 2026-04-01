package com.meta_forge_platform.platform.application.dto.menu;

import com.meta_forge_platform.platform.application.dto.module.DpModuleSummaryDto;
import com.meta_forge_platform.platform.application.dto.screen.DpScreenSummaryDto;
import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class DpMenuDto extends BaseDto {
    private DpModuleSummaryDto module;
    private String menuCode;
    private String menuName;
    private Long parentMenuId;
    private String parentMenuName;
    private String menuType;
    private DpScreenSummaryDto screen;
    private String routePath;
    private String icon;
    private Integer sortOrder;
    private Boolean isActive;
    private Map<String, Object> configJson;
    private Boolean isDeleted;
    private List<DpMenuSummaryDto> children;
}
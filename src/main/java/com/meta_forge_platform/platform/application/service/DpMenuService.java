package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.menu.*;
import com.meta_forge_platform.shared.application.BaseService;
import java.util.List;

public interface DpMenuService
        extends BaseService<DpMenuDto, CreateDpMenuCmd, UpdateDpMenuCmd, Long> {

    List<DpMenuSummaryDto> findRootMenusByModule(Long moduleId);
    List<DpMenuSummaryDto> findChildMenus(Long parentMenuId);
    List<DpMenuSummaryDto> findActiveMenusByModule(Long moduleId);
}
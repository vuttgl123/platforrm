package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.menu.CreateDpMenuCmd;
import com.meta_forge_platform.platform.application.dto.menu.DpMenuDto;
import com.meta_forge_platform.platform.application.dto.menu.DpMenuSummaryDto;
import com.meta_forge_platform.platform.application.dto.menu.UpdateDpMenuCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface DpMenuService extends CrudService<
        DpMenuDto,
        CreateDpMenuCmd,
        UpdateDpMenuCmd,
        Long> {

    List<DpMenuSummaryDto> findRootMenusByModule(Long moduleId);

    List<DpMenuSummaryDto> findChildMenus(Long parentMenuId);

    List<DpMenuSummaryDto> findActiveMenusByModule(Long moduleId);
}
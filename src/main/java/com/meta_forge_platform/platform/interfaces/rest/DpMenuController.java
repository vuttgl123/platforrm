package com.meta_forge_platform.platform.interfaces.rest;

import com.meta_forge_platform.platform.application.dto.menu.CreateDpMenuCmd;
import com.meta_forge_platform.platform.application.dto.menu.DpMenuDto;
import com.meta_forge_platform.platform.application.dto.menu.DpMenuSummaryDto;
import com.meta_forge_platform.platform.application.dto.menu.UpdateDpMenuCmd;
import com.meta_forge_platform.platform.application.service.DpMenuService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/menus")
public class DpMenuController extends BaseCrudController<
        DpMenuDto,
        CreateDpMenuCmd,
        UpdateDpMenuCmd,
        Long> {

    private final DpMenuService dpMenuService;

    public DpMenuController(DpMenuService dpMenuService,
                            ApiResponseFactory responseFactory) {
        super(dpMenuService, responseFactory);
        this.dpMenuService = dpMenuService;
    }

    @GetMapping("/root/{moduleId}")
    public ResponseEntity<ApiResponse<List<DpMenuSummaryDto>>> findRootMenusByModule(
            @PathVariable Long moduleId
    ) {
        return responseFactory
                .success(dpMenuService.findRootMenusByModule(moduleId))
                .toResponseEntity();
    }

    @GetMapping("/children/{parentMenuId}")
    public ResponseEntity<ApiResponse<List<DpMenuSummaryDto>>> findChildMenus(
            @PathVariable Long parentMenuId
    ) {
        return responseFactory
                .success(dpMenuService.findChildMenus(parentMenuId))
                .toResponseEntity();
    }

    @GetMapping("/active/{moduleId}")
    public ResponseEntity<ApiResponse<List<DpMenuSummaryDto>>> findActiveMenusByModule(
            @PathVariable Long moduleId
    ) {
        return responseFactory
                .success(dpMenuService.findActiveMenusByModule(moduleId))
                .toResponseEntity();
    }
}
package com.meta_forge_platform.platform.interfaces.rest;

import com.meta_forge_platform.platform.application.dto.module.CreateDpModuleCmd;
import com.meta_forge_platform.platform.application.dto.module.DpModuleDto;
import com.meta_forge_platform.platform.application.dto.module.DpModuleSummaryDto;
import com.meta_forge_platform.platform.application.dto.module.UpdateDpModuleCmd;
import com.meta_forge_platform.platform.application.service.DpModuleService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/modules")
public class DpModuleController extends BaseCrudController<
        DpModuleDto,
        CreateDpModuleCmd,
        UpdateDpModuleCmd,
        Long> {

    private final DpModuleService dpModuleService;

    public DpModuleController(DpModuleService dpModuleService,
                              ApiResponseFactory responseFactory) {
        super(dpModuleService, responseFactory);
        this.dpModuleService = dpModuleService;
    }

    @GetMapping("/by-code/{moduleCode}")
    public ResponseEntity<ApiResponse<DpModuleDto>> getByCode(
            @PathVariable String moduleCode
    ) {
        return responseFactory
                .success(dpModuleService.getByCode(moduleCode))
                .toResponseEntity();
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<List<DpModuleSummaryDto>>> findAllSummary() {
        return responseFactory
                .success(dpModuleService.findAllSummary())
                .toResponseEntity();
    }

    @GetMapping("/summary/active")
    public ResponseEntity<ApiResponse<List<DpModuleSummaryDto>>> findAllActiveSummary() {
        return responseFactory
                .success(dpModuleService.findAllActiveSummary())
                .toResponseEntity();
    }
}
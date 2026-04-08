package com.meta_forge_platform.platform.interfaces.rest;

import com.meta_forge_platform.platform.application.dto.screen.CreateDpScreenCmd;
import com.meta_forge_platform.platform.application.dto.screen.DpScreenDto;
import com.meta_forge_platform.platform.application.dto.screen.DpScreenSummaryDto;
import com.meta_forge_platform.platform.application.dto.screen.UpdateDpScreenCmd;
import com.meta_forge_platform.platform.application.service.DpScreenService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/screens")
public class DpScreenController extends BaseCrudController<
        DpScreenDto,
        CreateDpScreenCmd,
        UpdateDpScreenCmd,
        Long> {

    private final DpScreenService dpScreenService;

    public DpScreenController(DpScreenService dpScreenService,
                              ApiResponseFactory responseFactory) {
        super(dpScreenService, responseFactory);
        this.dpScreenService = dpScreenService;
    }

    @GetMapping("/by-module/{moduleId}")
    public ResponseEntity<ApiResponse<List<DpScreenSummaryDto>>> findAllByModule(
            @PathVariable Long moduleId
    ) {
        return responseFactory
                .success(dpScreenService.findAllByModule(moduleId))
                .toResponseEntity();
    }

    @GetMapping("/by-entity/{entityId}")
    public ResponseEntity<ApiResponse<List<DpScreenSummaryDto>>> findAllByEntity(
            @PathVariable Long entityId
    ) {
        return responseFactory
                .success(dpScreenService.findAllByEntity(entityId))
                .toResponseEntity();
    }
}
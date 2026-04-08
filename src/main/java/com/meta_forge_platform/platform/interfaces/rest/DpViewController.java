package com.meta_forge_platform.platform.interfaces.rest;

import com.meta_forge_platform.platform.application.dto.view.CreateDpViewCmd;
import com.meta_forge_platform.platform.application.dto.view.DpViewDto;
import com.meta_forge_platform.platform.application.dto.view.DpViewSummaryDto;
import com.meta_forge_platform.platform.application.dto.view.UpdateDpViewCmd;
import com.meta_forge_platform.platform.application.service.DpViewService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/views")
public class DpViewController extends BaseCrudController<
        DpViewDto,
        CreateDpViewCmd,
        UpdateDpViewCmd,
        Long> {

    private final DpViewService dpViewService;

    public DpViewController(DpViewService dpViewService,
                            ApiResponseFactory responseFactory) {
        super(dpViewService, responseFactory);
        this.dpViewService = dpViewService;
    }

    @GetMapping("/by-entity/{entityId}")
    public ResponseEntity<ApiResponse<List<DpViewSummaryDto>>> findAllByEntity(
            @PathVariable Long entityId
    ) {
        return responseFactory
                .success(dpViewService.findAllByEntity(entityId))
                .toResponseEntity();
    }

    @GetMapping("/default/{entityId}")
    public ResponseEntity<ApiResponse<DpViewDto>> getDefaultByEntity(
            @PathVariable Long entityId
    ) {
        return responseFactory
                .success(dpViewService.getDefaultByEntity(entityId))
                .toResponseEntity();
    }
}
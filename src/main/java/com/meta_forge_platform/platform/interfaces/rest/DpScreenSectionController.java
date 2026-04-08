package com.meta_forge_platform.platform.interfaces.rest;

import com.meta_forge_platform.platform.application.dto.screensection.CreateDpScreenSectionCmd;
import com.meta_forge_platform.platform.application.dto.screensection.DpScreenSectionDto;
import com.meta_forge_platform.platform.application.dto.screensection.DpScreenSectionSummaryDto;
import com.meta_forge_platform.platform.application.dto.screensection.UpdateDpScreenSectionCmd;
import com.meta_forge_platform.platform.application.service.DpScreenSectionService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/screen-sections")
public class DpScreenSectionController extends BaseCrudController<
        DpScreenSectionDto,
        CreateDpScreenSectionCmd,
        UpdateDpScreenSectionCmd,
        Long> {

    private final DpScreenSectionService dpScreenSectionService;

    public DpScreenSectionController(DpScreenSectionService dpScreenSectionService,
                                     ApiResponseFactory responseFactory) {
        super(dpScreenSectionService, responseFactory);
        this.dpScreenSectionService = dpScreenSectionService;
    }

    @GetMapping("/by-screen/{screenId}")
    public ResponseEntity<ApiResponse<List<DpScreenSectionSummaryDto>>> findAllByScreen(
            @PathVariable Long screenId
    ) {
        return responseFactory
                .success(dpScreenSectionService.findAllByScreen(screenId))
                .toResponseEntity();
    }

    @GetMapping("/root/{screenId}")
    public ResponseEntity<ApiResponse<List<DpScreenSectionSummaryDto>>> findRootSectionsByScreen(
            @PathVariable Long screenId
    ) {
        return responseFactory
                .success(dpScreenSectionService.findRootSectionsByScreen(screenId))
                .toResponseEntity();
    }

    @GetMapping("/children/{parentSectionId}")
    public ResponseEntity<ApiResponse<List<DpScreenSectionSummaryDto>>> findChildSections(
            @PathVariable Long parentSectionId
    ) {
        return responseFactory
                .success(dpScreenSectionService.findChildSections(parentSectionId))
                .toResponseEntity();
    }
}
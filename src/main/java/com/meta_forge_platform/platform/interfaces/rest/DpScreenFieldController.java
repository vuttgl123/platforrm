package com.meta_forge_platform.platform.interfaces.rest;

import com.meta_forge_platform.platform.application.dto.screenfield.CreateDpScreenFieldCmd;
import com.meta_forge_platform.platform.application.dto.screenfield.DpScreenFieldDto;
import com.meta_forge_platform.platform.application.dto.screenfield.DpScreenFieldSummaryDto;
import com.meta_forge_platform.platform.application.dto.screenfield.UpdateDpScreenFieldCmd;
import com.meta_forge_platform.platform.application.service.DpScreenFieldService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/screen-fields")
public class DpScreenFieldController extends BaseCrudController<
        DpScreenFieldDto,
        CreateDpScreenFieldCmd,
        UpdateDpScreenFieldCmd,
        Long> {

    private final DpScreenFieldService dpScreenFieldService;

    public DpScreenFieldController(DpScreenFieldService dpScreenFieldService,
                                   ApiResponseFactory responseFactory) {
        super(dpScreenFieldService, responseFactory);
        this.dpScreenFieldService = dpScreenFieldService;
    }

    @GetMapping("/by-screen/{screenId}")
    public ResponseEntity<ApiResponse<List<DpScreenFieldSummaryDto>>> findAllByScreen(
            @PathVariable Long screenId
    ) {
        return responseFactory
                .success(dpScreenFieldService.findAllByScreen(screenId))
                .toResponseEntity();
    }

    @GetMapping("/by-section/{sectionId}")
    public ResponseEntity<ApiResponse<List<DpScreenFieldSummaryDto>>> findAllBySection(
            @PathVariable Long sectionId
    ) {
        return responseFactory
                .success(dpScreenFieldService.findAllBySection(sectionId))
                .toResponseEntity();
    }
}
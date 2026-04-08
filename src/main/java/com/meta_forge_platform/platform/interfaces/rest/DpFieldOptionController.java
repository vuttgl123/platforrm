package com.meta_forge_platform.platform.interfaces.rest;

import com.meta_forge_platform.platform.application.dto.fieldoption.CreateDpFieldOptionCmd;
import com.meta_forge_platform.platform.application.dto.fieldoption.DpFieldOptionDto;
import com.meta_forge_platform.platform.application.dto.fieldoption.DpFieldOptionSummaryDto;
import com.meta_forge_platform.platform.application.dto.fieldoption.UpdateDpFieldOptionCmd;
import com.meta_forge_platform.platform.application.service.DpFieldOptionService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/field-options")
public class DpFieldOptionController extends BaseCrudController<
        DpFieldOptionDto,
        CreateDpFieldOptionCmd,
        UpdateDpFieldOptionCmd,
        Long> {

    private final DpFieldOptionService dpFieldOptionService;

    public DpFieldOptionController(DpFieldOptionService dpFieldOptionService,
                                   ApiResponseFactory responseFactory) {
        super(dpFieldOptionService, responseFactory);
        this.dpFieldOptionService = dpFieldOptionService;
    }

    @GetMapping("/by-field/{fieldId}")
    public ResponseEntity<ApiResponse<List<DpFieldOptionSummaryDto>>> findAllByField(
            @PathVariable Long fieldId
    ) {
        return responseFactory
                .success(dpFieldOptionService.findAllByField(fieldId))
                .toResponseEntity();
    }

    @GetMapping("/by-field/{fieldId}/active")
    public ResponseEntity<ApiResponse<List<DpFieldOptionSummaryDto>>> findActiveByField(
            @PathVariable Long fieldId
    ) {
        return responseFactory
                .success(dpFieldOptionService.findActiveByField(fieldId))
                .toResponseEntity();
    }
}
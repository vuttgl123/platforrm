package com.meta_forge_platform.platform.interfaces.rest;

import com.meta_forge_platform.platform.application.dto.field.CreateDpFieldCmd;
import com.meta_forge_platform.platform.application.dto.field.DpFieldDto;
import com.meta_forge_platform.platform.application.dto.field.DpFieldSummaryDto;
import com.meta_forge_platform.platform.application.dto.field.UpdateDpFieldCmd;
import com.meta_forge_platform.platform.application.service.DpFieldService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/fields")
public class DpFieldController extends BaseCrudController<
        DpFieldDto,
        CreateDpFieldCmd,
        UpdateDpFieldCmd,
        Long> {

    private final DpFieldService dpFieldService;

    public DpFieldController(DpFieldService dpFieldService,
                             ApiResponseFactory responseFactory) {
        super(dpFieldService, responseFactory);
        this.dpFieldService = dpFieldService;
    }

    @GetMapping("/by-entity/{entityId}")
    public ResponseEntity<ApiResponse<List<DpFieldSummaryDto>>> findAllByEntity(
            @PathVariable Long entityId
    ) {
        return responseFactory
                .success(dpFieldService.findAllByEntity(entityId))
                .toResponseEntity();
    }

    @GetMapping("/by-field-group/{groupId}")
    public ResponseEntity<ApiResponse<List<DpFieldSummaryDto>>> findAllByFieldGroup(
            @PathVariable Long groupId
    ) {
        return responseFactory
                .success(dpFieldService.findAllByFieldGroup(groupId))
                .toResponseEntity();
    }

    @GetMapping("/searchable/{entityId}")
    public ResponseEntity<ApiResponse<List<DpFieldSummaryDto>>> findSearchableFields(
            @PathVariable Long entityId
    ) {
        return responseFactory
                .success(dpFieldService.findSearchableFields(entityId))
                .toResponseEntity();
    }
}
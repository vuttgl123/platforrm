package com.meta_forge_platform.platform.interfaces.rest;

import com.meta_forge_platform.platform.application.dto.fieldgroup.CreateDpFieldGroupCmd;
import com.meta_forge_platform.platform.application.dto.fieldgroup.DpFieldGroupDto;
import com.meta_forge_platform.platform.application.dto.fieldgroup.DpFieldGroupSummaryDto;
import com.meta_forge_platform.platform.application.dto.fieldgroup.UpdateDpFieldGroupCmd;
import com.meta_forge_platform.platform.application.service.DpFieldGroupService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/field-groups")
public class DpFieldGroupController extends BaseCrudController<
        DpFieldGroupDto,
        CreateDpFieldGroupCmd,
        UpdateDpFieldGroupCmd,
        Long> {

    private final DpFieldGroupService dpFieldGroupService;

    public DpFieldGroupController(DpFieldGroupService dpFieldGroupService,
                                  ApiResponseFactory responseFactory) {
        super(dpFieldGroupService, responseFactory);
        this.dpFieldGroupService = dpFieldGroupService;
    }

    @GetMapping("/by-entity/{entityId}")
    public ResponseEntity<ApiResponse<List<DpFieldGroupSummaryDto>>> findAllByEntity(
            @PathVariable Long entityId
    ) {
        return responseFactory
                .success(dpFieldGroupService.findAllByEntity(entityId))
                .toResponseEntity();
    }
}
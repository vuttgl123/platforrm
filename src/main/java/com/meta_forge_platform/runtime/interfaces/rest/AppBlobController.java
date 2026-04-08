package com.meta_forge_platform.runtime.interfaces.rest;

import com.meta_forge_platform.runtime.application.dto.blob.AppBlobDto;
import com.meta_forge_platform.runtime.application.dto.blob.CreateAppBlobCmd;
import com.meta_forge_platform.runtime.application.dto.blob.UpdateAppBlobCmd;
import com.meta_forge_platform.runtime.application.service.AppBlobService;
import com.meta_forge_platform.shared.interfaces.ApiResponse;
import com.meta_forge_platform.shared.interfaces.ApiResponseFactory;
import com.meta_forge_platform.shared.interfaces.BaseCrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/runtime/blobs")
public class AppBlobController extends BaseCrudController<
        AppBlobDto,
        CreateAppBlobCmd,
        UpdateAppBlobCmd,
        Long> {

    private final AppBlobService appBlobService;

    public AppBlobController(AppBlobService appBlobService,
                             ApiResponseFactory responseFactory) {
        super(appBlobService, responseFactory);
        this.appBlobService = appBlobService;
    }

    @GetMapping("/by-code/{blobCode}")
    public ResponseEntity<ApiResponse<AppBlobDto>> getByCode(
            @PathVariable String blobCode
    ) {
        return responseFactory
                .success(appBlobService.getByCode(blobCode))
                .toResponseEntity();
    }
}
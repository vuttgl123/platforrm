package com.meta_forge_platform.runtime.application.service;

import com.meta_forge_platform.runtime.application.dto.blob.AppBlobDto;
import com.meta_forge_platform.runtime.application.dto.blob.CreateAppBlobCmd;
import com.meta_forge_platform.runtime.application.dto.blob.UpdateAppBlobCmd;
import com.meta_forge_platform.shared.application.CrudService;

public interface AppBlobService extends CrudService<
        AppBlobDto,
        CreateAppBlobCmd,
        UpdateAppBlobCmd,
        Long> {

    AppBlobDto getByCode(String blobCode);
}
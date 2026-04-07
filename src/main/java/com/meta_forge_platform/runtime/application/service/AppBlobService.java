package com.meta_forge_platform.runtime.application.service;

import com.meta_forge_platform.runtime.application.dto.blob.*;
import com.meta_forge_platform.shared.application.BaseService;

public interface AppBlobService
        extends BaseService<AppBlobDto, CreateAppBlobCmd, UpdateAppBlobCmd, Long> {

    AppBlobDto getByCode(String blobCode);
}
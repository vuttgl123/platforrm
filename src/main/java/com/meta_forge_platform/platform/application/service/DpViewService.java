package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.view.*;
import com.meta_forge_platform.shared.application.BaseService;
import java.util.List;

public interface DpViewService
        extends BaseService<DpViewDto, CreateDpViewCmd, UpdateDpViewCmd, Long> {

    List<DpViewSummaryDto> findAllByEntity(Long entityId);
    DpViewDto getDefaultByEntity(Long entityId);
}
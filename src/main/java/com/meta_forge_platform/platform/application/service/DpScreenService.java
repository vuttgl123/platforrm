package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.screen.*;
import com.meta_forge_platform.shared.application.BaseService;
import java.util.List;

public interface DpScreenService
        extends BaseService<DpScreenDto, CreateDpScreenCmd, UpdateDpScreenCmd, Long> {

    List<DpScreenSummaryDto> findAllByModule(Long moduleId);
    List<DpScreenSummaryDto> findAllByEntity(Long entityId);
}
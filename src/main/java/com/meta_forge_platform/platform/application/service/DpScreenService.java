package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.screen.CreateDpScreenCmd;
import com.meta_forge_platform.platform.application.dto.screen.DpScreenDto;
import com.meta_forge_platform.platform.application.dto.screen.DpScreenSummaryDto;
import com.meta_forge_platform.platform.application.dto.screen.UpdateDpScreenCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface DpScreenService extends CrudService<
        DpScreenDto,
        CreateDpScreenCmd,
        UpdateDpScreenCmd,
        Long> {

    List<DpScreenSummaryDto> findAllByModule(Long moduleId);

    List<DpScreenSummaryDto> findAllByEntity(Long entityId);
}
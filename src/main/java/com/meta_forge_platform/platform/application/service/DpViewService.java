package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.view.CreateDpViewCmd;
import com.meta_forge_platform.platform.application.dto.view.DpViewDto;
import com.meta_forge_platform.platform.application.dto.view.DpViewSummaryDto;
import com.meta_forge_platform.platform.application.dto.view.UpdateDpViewCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface DpViewService extends CrudService<
        DpViewDto,
        CreateDpViewCmd,
        UpdateDpViewCmd,
        Long> {

    List<DpViewSummaryDto> findAllByEntity(Long entityId);

    DpViewDto getDefaultByEntity(Long entityId);
}
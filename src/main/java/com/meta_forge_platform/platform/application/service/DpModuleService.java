package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.module.CreateDpModuleCmd;
import com.meta_forge_platform.platform.application.dto.module.DpModuleDto;
import com.meta_forge_platform.platform.application.dto.module.DpModuleSummaryDto;
import com.meta_forge_platform.platform.application.dto.module.UpdateDpModuleCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface DpModuleService extends CrudService<
        DpModuleDto,
        CreateDpModuleCmd,
        UpdateDpModuleCmd,
        Long> {

    DpModuleDto getByCode(String moduleCode);

    List<DpModuleSummaryDto> findAllSummary();

    List<DpModuleSummaryDto> findAllActiveSummary();
}
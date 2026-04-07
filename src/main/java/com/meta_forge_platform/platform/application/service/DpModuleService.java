package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.module.*;
import com.meta_forge_platform.shared.application.BaseService;
import java.util.List;

public interface DpModuleService
        extends BaseService<DpModuleDto, CreateDpModuleCmd, UpdateDpModuleCmd, Long> {

    DpModuleDto getByCode(String moduleCode);
    List<DpModuleSummaryDto> findAllSummary();
    List<DpModuleSummaryDto> findAllActiveSummary();
}
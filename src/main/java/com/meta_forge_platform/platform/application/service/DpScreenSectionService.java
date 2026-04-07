package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.screensection.*;
import com.meta_forge_platform.shared.application.BaseService;
import java.util.List;

public interface DpScreenSectionService
        extends BaseService<DpScreenSectionDto, CreateDpScreenSectionCmd, UpdateDpScreenSectionCmd, Long> {

    List<DpScreenSectionSummaryDto> findAllByScreen(Long screenId);
    List<DpScreenSectionSummaryDto> findRootSectionsByScreen(Long screenId);
    List<DpScreenSectionSummaryDto> findChildSections(Long parentSectionId);
}
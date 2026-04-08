package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.screensection.CreateDpScreenSectionCmd;
import com.meta_forge_platform.platform.application.dto.screensection.DpScreenSectionDto;
import com.meta_forge_platform.platform.application.dto.screensection.DpScreenSectionSummaryDto;
import com.meta_forge_platform.platform.application.dto.screensection.UpdateDpScreenSectionCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface DpScreenSectionService extends CrudService<
        DpScreenSectionDto,
        CreateDpScreenSectionCmd,
        UpdateDpScreenSectionCmd,
        Long> {

    List<DpScreenSectionSummaryDto> findAllByScreen(Long screenId);

    List<DpScreenSectionSummaryDto> findRootSectionsByScreen(Long screenId);

    List<DpScreenSectionSummaryDto> findChildSections(Long parentSectionId);
}
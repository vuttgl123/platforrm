package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.screenfield.CreateDpScreenFieldCmd;
import com.meta_forge_platform.platform.application.dto.screenfield.DpScreenFieldDto;
import com.meta_forge_platform.platform.application.dto.screenfield.DpScreenFieldSummaryDto;
import com.meta_forge_platform.platform.application.dto.screenfield.UpdateDpScreenFieldCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface DpScreenFieldService extends CrudService<
        DpScreenFieldDto,
        CreateDpScreenFieldCmd,
        UpdateDpScreenFieldCmd,
        Long> {

    List<DpScreenFieldSummaryDto> findAllByScreen(Long screenId);

    List<DpScreenFieldSummaryDto> findAllBySection(Long sectionId);
}
package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.fieldoption.CreateDpFieldOptionCmd;
import com.meta_forge_platform.platform.application.dto.fieldoption.DpFieldOptionDto;
import com.meta_forge_platform.platform.application.dto.fieldoption.DpFieldOptionSummaryDto;
import com.meta_forge_platform.platform.application.dto.fieldoption.UpdateDpFieldOptionCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface DpFieldOptionService extends CrudService<
        DpFieldOptionDto,
        CreateDpFieldOptionCmd,
        UpdateDpFieldOptionCmd,
        Long> {

    List<DpFieldOptionSummaryDto> findAllByField(Long fieldId);

    List<DpFieldOptionSummaryDto> findActiveByField(Long fieldId);
}
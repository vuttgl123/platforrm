package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.fieldoption.*;
import com.meta_forge_platform.shared.application.BaseService;
import java.util.List;

public interface DpFieldOptionService
        extends BaseService<DpFieldOptionDto, CreateDpFieldOptionCmd, UpdateDpFieldOptionCmd, Long> {

    List<DpFieldOptionSummaryDto> findAllByField(Long fieldId);
    List<DpFieldOptionSummaryDto> findActiveByField(Long fieldId);
}
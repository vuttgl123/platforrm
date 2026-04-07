package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.screenfield.*;
import com.meta_forge_platform.shared.application.BaseService;
import java.util.List;

public interface DpScreenFieldService
        extends BaseService<DpScreenFieldDto, CreateDpScreenFieldCmd, UpdateDpScreenFieldCmd, Long> {

    List<DpScreenFieldSummaryDto> findAllByScreen(Long screenId);
    List<DpScreenFieldSummaryDto> findAllBySection(Long sectionId);
}
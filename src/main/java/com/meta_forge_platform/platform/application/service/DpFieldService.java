package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.field.*;
import com.meta_forge_platform.shared.application.BaseService;
import java.util.List;

public interface DpFieldService
        extends BaseService<DpFieldDto, CreateDpFieldCmd, UpdateDpFieldCmd, Long> {

    List<DpFieldSummaryDto> findAllByEntity(Long entityId);
    List<DpFieldSummaryDto> findAllByFieldGroup(Long groupId);
    List<DpFieldSummaryDto> findSearchableFields(Long entityId);
}
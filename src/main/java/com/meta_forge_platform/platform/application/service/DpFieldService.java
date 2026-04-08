package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.field.CreateDpFieldCmd;
import com.meta_forge_platform.platform.application.dto.field.DpFieldDto;
import com.meta_forge_platform.platform.application.dto.field.DpFieldSummaryDto;
import com.meta_forge_platform.platform.application.dto.field.UpdateDpFieldCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface DpFieldService extends CrudService<
        DpFieldDto,
        CreateDpFieldCmd,
        UpdateDpFieldCmd,
        Long> {

    List<DpFieldSummaryDto> findAllByEntity(Long entityId);

    List<DpFieldSummaryDto> findAllByFieldGroup(Long groupId);

    List<DpFieldSummaryDto> findSearchableFields(Long entityId);
}
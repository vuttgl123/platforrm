package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.fieldgroup.CreateDpFieldGroupCmd;
import com.meta_forge_platform.platform.application.dto.fieldgroup.DpFieldGroupDto;
import com.meta_forge_platform.platform.application.dto.fieldgroup.DpFieldGroupSummaryDto;
import com.meta_forge_platform.platform.application.dto.fieldgroup.UpdateDpFieldGroupCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface DpFieldGroupService extends CrudService<
        DpFieldGroupDto,
        CreateDpFieldGroupCmd,
        UpdateDpFieldGroupCmd,
        Long> {

    List<DpFieldGroupSummaryDto> findAllByEntity(Long entityId);
}
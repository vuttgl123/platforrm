package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.fieldgroup.*;
import com.meta_forge_platform.shared.application.BaseService;
import java.util.List;

public interface DpFieldGroupService
        extends BaseService<DpFieldGroupDto, CreateDpFieldGroupCmd, UpdateDpFieldGroupCmd, Long> {

    List<DpFieldGroupSummaryDto> findAllByEntity(Long entityId);
}
package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.entityconstraint.*;
import com.meta_forge_platform.shared.application.BaseService;
import java.util.List;

public interface DpEntityConstraintService
        extends BaseService<DpEntityConstraintDto, CreateDpEntityConstraintCmd, UpdateDpEntityConstraintCmd, Long> {

    List<DpEntityConstraintSummaryDto> findAllByEntity(Long entityId);
    List<DpEntityConstraintSummaryDto> findActiveByEntity(Long entityId);
}
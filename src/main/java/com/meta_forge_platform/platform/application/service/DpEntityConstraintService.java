package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.entityconstraint.CreateDpEntityConstraintCmd;
import com.meta_forge_platform.platform.application.dto.entityconstraint.DpEntityConstraintDto;
import com.meta_forge_platform.platform.application.dto.entityconstraint.DpEntityConstraintSummaryDto;
import com.meta_forge_platform.platform.application.dto.entityconstraint.UpdateDpEntityConstraintCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface DpEntityConstraintService extends CrudService<
        DpEntityConstraintDto,
        CreateDpEntityConstraintCmd,
        UpdateDpEntityConstraintCmd,
        Long> {

    List<DpEntityConstraintSummaryDto> findAllByEntity(Long entityId);

    List<DpEntityConstraintSummaryDto> findActiveByEntity(Long entityId);
}
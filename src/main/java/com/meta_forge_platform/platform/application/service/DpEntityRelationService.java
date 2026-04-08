package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.entityrelation.CreateDpEntityRelationCmd;
import com.meta_forge_platform.platform.application.dto.entityrelation.DpEntityRelationDto;
import com.meta_forge_platform.platform.application.dto.entityrelation.DpEntityRelationSummaryDto;
import com.meta_forge_platform.platform.application.dto.entityrelation.UpdateDpEntityRelationCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface DpEntityRelationService extends CrudService<
        DpEntityRelationDto,
        CreateDpEntityRelationCmd,
        UpdateDpEntityRelationCmd,
        Long> {

    List<DpEntityRelationSummaryDto> findAllBySourceEntity(Long sourceEntityId);

    List<DpEntityRelationSummaryDto> findAllByTargetEntity(Long targetEntityId);
}
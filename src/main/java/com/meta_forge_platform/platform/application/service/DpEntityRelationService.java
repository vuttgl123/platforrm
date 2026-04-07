package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.entityrelation.*;
import com.meta_forge_platform.shared.application.BaseService;
import java.util.List;

public interface DpEntityRelationService
        extends BaseService<DpEntityRelationDto, CreateDpEntityRelationCmd, UpdateDpEntityRelationCmd, Long> {

    List<DpEntityRelationSummaryDto> findAllBySourceEntity(Long sourceEntityId);
    List<DpEntityRelationSummaryDto> findAllByTargetEntity(Long targetEntityId);
}
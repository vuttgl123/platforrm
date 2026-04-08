package com.meta_forge_platform.platform.application.service;

import com.meta_forge_platform.platform.application.dto.entity.CreateDpEntityCmd;
import com.meta_forge_platform.platform.application.dto.entity.DpEntityDto;
import com.meta_forge_platform.platform.application.dto.entity.DpEntitySummaryDto;
import com.meta_forge_platform.platform.application.dto.entity.UpdateDpEntityCmd;
import com.meta_forge_platform.shared.application.CrudService;

import java.util.List;

public interface DpEntityService extends CrudService<
        DpEntityDto,
        CreateDpEntityCmd,
        UpdateDpEntityCmd,
        Long> {

    DpEntityDto getByCode(Long moduleId, String entityCode);

    List<DpEntitySummaryDto> findAllByModule(Long moduleId);

    List<DpEntitySummaryDto> findRootEntitiesByModule(Long moduleId);

    List<DpEntitySummaryDto> findAllSummary();
}
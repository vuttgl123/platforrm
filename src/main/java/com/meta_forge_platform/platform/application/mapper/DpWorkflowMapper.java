package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.workflow.CreateDpWorkflowCmd;
import com.meta_forge_platform.platform.application.dto.workflow.DpWorkflowDto;
import com.meta_forge_platform.platform.application.dto.workflow.DpWorkflowSummaryDto;
import com.meta_forge_platform.platform.application.dto.workflow.UpdateDpWorkflowCmd;
import com.meta_forge_platform.platform.domain.entity.DpWorkflow;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {DpEntityMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface DpWorkflowMapper extends BaseMapper<
        DpWorkflow,
        DpWorkflowDto,
        DpWorkflowSummaryDto,
        CreateDpWorkflowCmd,
        UpdateDpWorkflowCmd> {

    @Override
    @Mapping(target = "entity", source = "entity")
    DpWorkflowDto toDto(DpWorkflow entity);

    @Override
    @Mapping(target = "entityId", source = "entity.id")
    DpWorkflowSummaryDto toSummaryDto(DpWorkflow entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entity", ignore = true)
    @Mapping(target = "states", ignore = true)
    @Mapping(target = "transitions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    DpWorkflow toEntity(CreateDpWorkflowCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workflowCode", ignore = true)
    @Mapping(target = "entity", ignore = true)
    @Mapping(target = "states", ignore = true)
    @Mapping(target = "transitions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(@MappingTarget DpWorkflow entity, UpdateDpWorkflowCmd command);

    @Override
    List<DpWorkflowDto> toDtoList(List<DpWorkflow> entities);

    @Override
    List<DpWorkflowSummaryDto> toSummaryDtoList(List<DpWorkflow> entities);
}
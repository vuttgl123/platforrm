package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.workflowstate.*;
import com.meta_forge_platform.platform.domain.entity.DpWorkflowState;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {DpWorkflowMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DpWorkflowStateMapper extends BaseMapper<DpWorkflowState, DpWorkflowStateDto, DpWorkflowStateSummaryDto, CreateDpWorkflowStateCmd, UpdateDpWorkflowStateCmd> {

    @Override
    @Mapping(target = "workflow", source = "workflow")
    DpWorkflowStateDto toDto(DpWorkflowState entity);

    @Override
    @Mapping(target = "workflowId", source = "workflow.id")
    DpWorkflowStateSummaryDto toSummaryDto(DpWorkflowState entity);

    @Override
    @Mapping(target = "id",                  ignore = true)
    @Mapping(target = "workflow",            ignore = true)
    @Mapping(target = "outgoingTransitions", ignore = true)
    @Mapping(target = "incomingTransitions", ignore = true)
    @Mapping(target = "createdAt",           ignore = true)
    @Mapping(target = "updatedAt",           ignore = true)
    @Mapping(target = "versionNo",           ignore = true)
    @Mapping(target = "createdBy",           ignore = true)
    @Mapping(target = "updatedBy",           ignore = true)
    @Mapping(target = "isDeleted",           ignore = true)
    @Mapping(target = "deletedAt",           ignore = true)
    @Mapping(target = "deletedBy",           ignore = true)
    DpWorkflowState toEntity(CreateDpWorkflowStateCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",                  ignore = true)
    @Mapping(target = "stateCode",           ignore = true)
    @Mapping(target = "workflow",            ignore = true)
    @Mapping(target = "outgoingTransitions", ignore = true)
    @Mapping(target = "incomingTransitions", ignore = true)
    @Mapping(target = "createdAt",           ignore = true)
    @Mapping(target = "updatedAt",           ignore = true)
    @Mapping(target = "createdBy",           ignore = true)
    @Mapping(target = "updatedBy",           ignore = true)
    @Mapping(target = "isDeleted",           ignore = true)
    @Mapping(target = "deletedAt",           ignore = true)
    @Mapping(target = "deletedBy",           ignore = true)
    void updateEntity(@MappingTarget DpWorkflowState entity, UpdateDpWorkflowStateCmd command);

    @Override List<DpWorkflowStateDto> toDtoList(List<DpWorkflowState> entities);
    @Override List<DpWorkflowStateSummaryDto> toSummaryDtoList(List<DpWorkflowState> entities);
}
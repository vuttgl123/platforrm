package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.workflowtransition.*;
import com.meta_forge_platform.platform.domain.entity.DpWorkflowTransition;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {DpWorkflowMapper.class, DpWorkflowStateMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DpWorkflowTransitionMapper extends BaseMapper<DpWorkflowTransition, DpWorkflowTransitionDto, DpWorkflowTransitionSummaryDto, CreateDpWorkflowTransitionCmd, UpdateDpWorkflowTransitionCmd> {

    @Override
    @Mapping(target = "workflow",  source = "workflow")
    @Mapping(target = "fromState", source = "fromState")
    @Mapping(target = "toState",   source = "toState")
    DpWorkflowTransitionDto toDto(DpWorkflowTransition entity);

    @Override
    @Mapping(target = "fromStateId",   source = "fromState.id")
    @Mapping(target = "fromStateName", source = "fromState.stateName")
    @Mapping(target = "toStateId",     source = "toState.id")
    @Mapping(target = "toStateName",   source = "toState.stateName")
    DpWorkflowTransitionSummaryDto toSummaryDto(DpWorkflowTransition entity);

    @Override
    @Mapping(target = "id",             ignore = true)
    @Mapping(target = "workflow",       ignore = true)
    @Mapping(target = "fromState",      ignore = true)
    @Mapping(target = "toState",        ignore = true)
    @Mapping(target = "createdAt",      ignore = true)
    @Mapping(target = "updatedAt",      ignore = true)
    @Mapping(target = "versionNo",      ignore = true)
    @Mapping(target = "createdBy",      ignore = true)
    @Mapping(target = "updatedBy",      ignore = true)
    @Mapping(target = "isDeleted",      ignore = true)
    @Mapping(target = "deletedAt",      ignore = true)
    @Mapping(target = "deletedBy",      ignore = true)
    DpWorkflowTransition toEntity(CreateDpWorkflowTransitionCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",             ignore = true)
    @Mapping(target = "transitionCode", ignore = true)
    @Mapping(target = "workflow",       ignore = true)
    @Mapping(target = "fromState",      ignore = true)
    @Mapping(target = "toState",        ignore = true)
    @Mapping(target = "createdAt",      ignore = true)
    @Mapping(target = "updatedAt",      ignore = true)
    @Mapping(target = "createdBy",      ignore = true)
    @Mapping(target = "updatedBy",      ignore = true)
    @Mapping(target = "isDeleted",      ignore = true)
    @Mapping(target = "deletedAt",      ignore = true)
    @Mapping(target = "deletedBy",      ignore = true)
    void updateEntity(@MappingTarget DpWorkflowTransition entity, UpdateDpWorkflowTransitionCmd command);

    @Override List<DpWorkflowTransitionDto> toDtoList(List<DpWorkflowTransition> entities);
    @Override List<DpWorkflowTransitionSummaryDto> toSummaryDtoList(List<DpWorkflowTransition> entities);
}
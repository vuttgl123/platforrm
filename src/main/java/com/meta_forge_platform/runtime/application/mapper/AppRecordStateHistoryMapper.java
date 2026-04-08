package com.meta_forge_platform.runtime.application.mapper;

import com.meta_forge_platform.runtime.application.dto.recordstatehistory.AppRecordStateHistoryDto;
import com.meta_forge_platform.runtime.application.dto.recordstatehistory.AppRecordStateHistorySummaryDto;
import com.meta_forge_platform.runtime.domain.entity.AppRecordStateHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppRecordStateHistoryMapper {

    @Mapping(target = "recordId", source = "record.id")
    @Mapping(target = "recordCode", source = "record.recordCode")
    @Mapping(target = "recordDisplayName", source = "record.displayName")
    @Mapping(target = "workflowId", source = "workflow.id")
    @Mapping(target = "workflowCode", source = "workflow.workflowCode")
    @Mapping(target = "workflowName", source = "workflow.workflowName")
    @Mapping(target = "fromStateId", source = "fromState.id")
    @Mapping(target = "fromStateCode", source = "fromState.stateCode")
    @Mapping(target = "fromStateName", source = "fromState.stateName")
    @Mapping(target = "toStateId", source = "toState.id")
    @Mapping(target = "toStateCode", source = "toState.stateCode")
    @Mapping(target = "toStateName", source = "toState.stateName")
    @Mapping(target = "toStateColor", source = "toState.colorCode")
    @Mapping(target = "transitionId", source = "transition.id")
    @Mapping(target = "transitionCode", source = "transition.transitionCode")
    @Mapping(target = "transitionName", source = "transition.transitionName")
    AppRecordStateHistoryDto toDto(AppRecordStateHistory entity);

    @Mapping(target = "recordId", source = "record.id")
    @Mapping(target = "workflowId", source = "workflow.id")
    @Mapping(target = "fromStateId", source = "fromState.id")
    @Mapping(target = "fromStateName", source = "fromState.stateName")
    @Mapping(target = "toStateId", source = "toState.id")
    @Mapping(target = "toStateName", source = "toState.stateName")
    @Mapping(target = "toStateColor", source = "toState.colorCode")
    AppRecordStateHistorySummaryDto toSummaryDto(AppRecordStateHistory entity);

    List<AppRecordStateHistoryDto> toDtoList(List<AppRecordStateHistory> entities);

    List<AppRecordStateHistorySummaryDto> toSummaryDtoList(List<AppRecordStateHistory> entities);
}
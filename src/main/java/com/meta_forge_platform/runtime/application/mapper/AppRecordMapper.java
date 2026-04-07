package com.meta_forge_platform.runtime.application.mapper;

import com.meta_forge_platform.platform.application.mapper.DpEntityMapper;
import com.meta_forge_platform.platform.application.mapper.DpWorkflowStateMapper;
import com.meta_forge_platform.runtime.application.dto.record.AppRecordDto;
import com.meta_forge_platform.runtime.application.dto.record.AppRecordSummaryDto;
import com.meta_forge_platform.runtime.application.dto.record.CreateAppRecordCmd;
import com.meta_forge_platform.runtime.application.dto.record.UpdateAppRecordCmd;
import com.meta_forge_platform.runtime.domain.entity.AppRecord;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {DpEntityMapper.class, DpWorkflowStateMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface AppRecordMapper extends BaseMapper<
        AppRecord,
        AppRecordDto,
        AppRecordSummaryDto,
        CreateAppRecordCmd,
        UpdateAppRecordCmd> {

    @Override
    @Mapping(target = "entity", source = "entity")
    @Mapping(target = "currentState", source = "currentState")
    @Mapping(target = "parentRecordId", source = "parentRecordId")
    @Mapping(target = "parentDisplayName", ignore = true)
    @Mapping(target = "rootRecordId", source = "rootRecordId")
    AppRecordDto toDto(AppRecord entity);

    @Override
    @Mapping(target = "entityId", source = "entity.id")
    @Mapping(target = "entityName", source = "entity.entityName")
    @Mapping(target = "currentStateId", source = "currentState.id")
    @Mapping(target = "currentStateName", source = "currentState.stateName")
    @Mapping(target = "currentStateColor", source = "currentState.colorCode")
    AppRecordSummaryDto toSummaryDto(AppRecord entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entity", ignore = true)
    @Mapping(target = "currentState", ignore = true)
    @Mapping(target = "parentRecordId", ignore = true)
    @Mapping(target = "rootRecordId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    AppRecord toEntity(CreateAppRecordCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recordCode", ignore = true)
    @Mapping(target = "entity", ignore = true)
    @Mapping(target = "currentState", ignore = true)
    @Mapping(target = "parentRecordId", ignore = true)
    @Mapping(target = "rootRecordId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(@MappingTarget AppRecord entity, UpdateAppRecordCmd command);

    @Override
    List<AppRecordDto> toDtoList(List<AppRecord> entities);

    @Override
    List<AppRecordSummaryDto> toSummaryDtoList(List<AppRecord> entities);
}
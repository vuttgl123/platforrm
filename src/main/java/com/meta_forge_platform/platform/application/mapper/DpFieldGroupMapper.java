package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.fieldgroup.*;
import com.meta_forge_platform.platform.domain.entity.DpFieldGroup;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {DpEntityMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DpFieldGroupMapper extends BaseMapper<DpFieldGroup, DpFieldGroupDto, DpFieldGroupSummaryDto, CreateDpFieldGroupCmd, UpdateDpFieldGroupCmd> {

    @Override
    @Mapping(target = "entity", source = "entity")
    DpFieldGroupDto toDto(DpFieldGroup entity);

    @Override
    @Mapping(target = "entityId", source = "entity.id")
    DpFieldGroupSummaryDto toSummaryDto(DpFieldGroup entity);

    @Override
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "entity",    ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "fields",    ignore = true)
    DpFieldGroup toEntity(CreateDpFieldGroupCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "groupCode", ignore = true)
    @Mapping(target = "entity",    ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "fields",    ignore = true)
    void updateEntity(@MappingTarget DpFieldGroup entity, UpdateDpFieldGroupCmd command);

    @Override List<DpFieldGroupDto> toDtoList(List<DpFieldGroup> entities);
    @Override List<DpFieldGroupSummaryDto> toSummaryDtoList(List<DpFieldGroup> entities);
}
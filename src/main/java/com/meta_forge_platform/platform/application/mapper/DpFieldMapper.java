package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.field.*;
import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {DpEntityMapper.class, DpFieldGroupMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DpFieldMapper extends BaseMapper<DpField, DpFieldDto, DpFieldSummaryDto, CreateDpFieldCmd, UpdateDpFieldCmd> {

    @Override
    @Mapping(target = "entity",         source = "entity")
    @Mapping(target = "fieldGroup",     source = "fieldGroup")
    @Mapping(target = "relationEntity", source = "relationEntity")
    DpFieldDto toDto(DpField entity);

    @Override
    @Mapping(target = "entityId",     source = "entity.id")
    @Mapping(target = "fieldGroupId", source = "fieldGroup.id")
    DpFieldSummaryDto toSummaryDto(DpField entity);

    @Override
    @Mapping(target = "id",             ignore = true)
    @Mapping(target = "entity",         ignore = true)
    @Mapping(target = "fieldGroup",     ignore = true)
    @Mapping(target = "relationEntity", ignore = true)
    @Mapping(target = "createdAt",      ignore = true)
    @Mapping(target = "updatedAt",      ignore = true)
    @Mapping(target = "versionNo",      ignore = true)
    @Mapping(target = "createdBy",      ignore = true)
    @Mapping(target = "updatedBy",      ignore = true)
    @Mapping(target = "isDeleted",      ignore = true)
    @Mapping(target = "deletedAt",      ignore = true)
    @Mapping(target = "deletedBy",      ignore = true)
    @Mapping(target = "options",        ignore = true)
    DpField toEntity(CreateDpFieldCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",             ignore = true)
    @Mapping(target = "fieldCode",      ignore = true)
    @Mapping(target = "entity",         ignore = true)
    @Mapping(target = "fieldGroup",     ignore = true)
    @Mapping(target = "relationEntity", ignore = true)
    @Mapping(target = "createdAt",      ignore = true)
    @Mapping(target = "updatedAt",      ignore = true)
    @Mapping(target = "createdBy",      ignore = true)
    @Mapping(target = "updatedBy",      ignore = true)
    @Mapping(target = "isDeleted",      ignore = true)
    @Mapping(target = "deletedAt",      ignore = true)
    @Mapping(target = "deletedBy",      ignore = true)
    @Mapping(target = "options",        ignore = true)
    void updateEntity(@MappingTarget DpField entity, UpdateDpFieldCmd command);

    @Override List<DpFieldDto> toDtoList(List<DpField> entities);
    @Override List<DpFieldSummaryDto> toSummaryDtoList(List<DpField> entities);
}
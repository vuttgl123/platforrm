package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.screenfield.*;
import com.meta_forge_platform.platform.domain.entity.DpScreenField;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {DpScreenMapper.class, DpScreenSectionMapper.class, DpFieldMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DpScreenFieldMapper extends BaseMapper<DpScreenField, DpScreenFieldDto, DpScreenFieldSummaryDto, CreateDpScreenFieldCmd, UpdateDpScreenFieldCmd> {

    @Override
    @Mapping(target = "screen",  source = "screen")
    @Mapping(target = "section", source = "section")
    @Mapping(target = "field",   source = "field")
    DpScreenFieldDto toDto(DpScreenField entity);

    @Override
    @Mapping(target = "screenId",  source = "screen.id")
    @Mapping(target = "sectionId", source = "section.id")
    @Mapping(target = "fieldId",   source = "field.id")
    @Mapping(target = "fieldCode", source = "field.fieldCode")
    @Mapping(target = "fieldName", source = "field.fieldName")
    DpScreenFieldSummaryDto toSummaryDto(DpScreenField entity);

    @Override
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "screen",    ignore = true)
    @Mapping(target = "section",   ignore = true)
    @Mapping(target = "field",     ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    DpScreenField toEntity(CreateDpScreenFieldCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "screen",    ignore = true)
    @Mapping(target = "section",   ignore = true)
    @Mapping(target = "field",     ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(@MappingTarget DpScreenField entity, UpdateDpScreenFieldCmd command);

    @Override List<DpScreenFieldDto> toDtoList(List<DpScreenField> entities);
    @Override List<DpScreenFieldSummaryDto> toSummaryDtoList(List<DpScreenField> entities);
}
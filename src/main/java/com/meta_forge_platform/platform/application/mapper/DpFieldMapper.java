package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.field.CreateDpFieldCmd;
import com.meta_forge_platform.platform.application.dto.field.DpFieldDto;
import com.meta_forge_platform.platform.application.dto.field.DpFieldSummaryDto;
import com.meta_forge_platform.platform.application.dto.field.UpdateDpFieldCmd;
import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {DpEntityMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface DpFieldMapper extends BaseMapper<
        DpField,
        DpFieldDto,
        DpFieldSummaryDto,
        CreateDpFieldCmd,
        UpdateDpFieldCmd> {

    @Override
    @Mapping(target = "entity", source = "entity")
    @Mapping(target = "relationEntity", source = "relationEntity")
    DpFieldDto toDto(DpField entity);

    @Override
    @Mapping(target = "entityId", source = "entity.id")
    DpFieldSummaryDto toSummaryDto(DpField entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entity", ignore = true)
    @Mapping(target = "relationEntity", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    DpField toEntity(CreateDpFieldCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fieldCode", ignore = true)
    @Mapping(target = "entity", ignore = true)
    @Mapping(target = "relationEntity", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(@MappingTarget DpField entity, UpdateDpFieldCmd command);

    @Override
    List<DpFieldDto> toDtoList(List<DpField> entities);

    @Override
    List<DpFieldSummaryDto> toSummaryDtoList(List<DpField> entities);
}
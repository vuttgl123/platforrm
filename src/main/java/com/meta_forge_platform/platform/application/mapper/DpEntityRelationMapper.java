package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.entityrelation.*;
import com.meta_forge_platform.platform.domain.entity.DpEntityRelation;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {DpEntityMapper.class, DpFieldMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DpEntityRelationMapper extends BaseMapper<DpEntityRelation, DpEntityRelationDto, DpEntityRelationSummaryDto, CreateDpEntityRelationCmd, UpdateDpEntityRelationCmd> {

    @Override
    @Mapping(target = "sourceEntity",  source = "sourceEntity")
    @Mapping(target = "targetEntity",  source = "targetEntity")
    @Mapping(target = "mappedByField", source = "mappedByField")
    DpEntityRelationDto toDto(DpEntityRelation entity);

    @Override
    @Mapping(target = "sourceEntityId", source = "sourceEntity.id")
    @Mapping(target = "targetEntityId", source = "targetEntity.id")
    DpEntityRelationSummaryDto toSummaryDto(DpEntityRelation entity);

    @Override
    @Mapping(target = "id",            ignore = true)
    @Mapping(target = "sourceEntity",  ignore = true)
    @Mapping(target = "targetEntity",  ignore = true)
    @Mapping(target = "mappedByField", ignore = true)
    @Mapping(target = "createdAt",     ignore = true)
    @Mapping(target = "updatedAt",     ignore = true)
    @Mapping(target = "versionNo",     ignore = true)
    @Mapping(target = "createdBy",     ignore = true)
    @Mapping(target = "updatedBy",     ignore = true)
    @Mapping(target = "isDeleted",     ignore = true)
    @Mapping(target = "deletedAt",     ignore = true)
    @Mapping(target = "deletedBy",     ignore = true)
    DpEntityRelation toEntity(CreateDpEntityRelationCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",            ignore = true)
    @Mapping(target = "relationCode",  ignore = true)
    @Mapping(target = "sourceEntity",  ignore = true)
    @Mapping(target = "targetEntity",  ignore = true)
    @Mapping(target = "mappedByField", ignore = true)
    @Mapping(target = "createdAt",     ignore = true)
    @Mapping(target = "updatedAt",     ignore = true)
    @Mapping(target = "createdBy",     ignore = true)
    @Mapping(target = "updatedBy",     ignore = true)
    @Mapping(target = "isDeleted",     ignore = true)
    @Mapping(target = "deletedAt",     ignore = true)
    @Mapping(target = "deletedBy",     ignore = true)
    void updateEntity(@MappingTarget DpEntityRelation entity, UpdateDpEntityRelationCmd command);

    @Override List<DpEntityRelationDto> toDtoList(List<DpEntityRelation> entities);
    @Override List<DpEntityRelationSummaryDto> toSummaryDtoList(List<DpEntityRelation> entities);
}
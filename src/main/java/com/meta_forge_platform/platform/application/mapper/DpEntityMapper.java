package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.entity.*;
import com.meta_forge_platform.platform.domain.entity.DpEntity;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {DpModuleMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DpEntityMapper extends BaseMapper<DpEntity, DpEntityDto, DpEntitySummaryDto, CreateDpEntityCmd, UpdateDpEntityCmd> {

    @Override
    @Mapping(target = "module", source = "module")
    DpEntityDto toDto(DpEntity entity);

    @Override
    @Mapping(target = "moduleId",   source = "module.id")
    @Mapping(target = "moduleName", source = "module.moduleName")
    DpEntitySummaryDto toSummaryDto(DpEntity entity);

    @Override
    @Mapping(target = "id",              ignore = true)
    @Mapping(target = "module",          ignore = true)
    @Mapping(target = "createdAt",       ignore = true)
    @Mapping(target = "updatedAt",       ignore = true)
    @Mapping(target = "versionNo",       ignore = true)
    @Mapping(target = "createdBy",       ignore = true)
    @Mapping(target = "updatedBy",       ignore = true)
    @Mapping(target = "isDeleted",       ignore = true)
    @Mapping(target = "deletedAt",       ignore = true)
    @Mapping(target = "deletedBy",       ignore = true)
    @Mapping(target = "fieldGroups",     ignore = true)
    @Mapping(target = "fields",          ignore = true)
    @Mapping(target = "sourceRelations", ignore = true)
    @Mapping(target = "targetRelations", ignore = true)
    @Mapping(target = "constraints",     ignore = true)
    @Mapping(target = "workflows",       ignore = true)
    @Mapping(target = "views",           ignore = true)
    DpEntity toEntity(CreateDpEntityCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",              ignore = true)
    @Mapping(target = "entityCode",      ignore = true)
    @Mapping(target = "module",          ignore = true)
    @Mapping(target = "createdAt",       ignore = true)
    @Mapping(target = "updatedAt",       ignore = true)
    @Mapping(target = "createdBy",       ignore = true)
    @Mapping(target = "updatedBy",       ignore = true)
    @Mapping(target = "isDeleted",       ignore = true)
    @Mapping(target = "deletedAt",       ignore = true)
    @Mapping(target = "deletedBy",       ignore = true)
    @Mapping(target = "fieldGroups",     ignore = true)
    @Mapping(target = "fields",          ignore = true)
    @Mapping(target = "sourceRelations", ignore = true)
    @Mapping(target = "targetRelations", ignore = true)
    @Mapping(target = "constraints",     ignore = true)
    @Mapping(target = "workflows",       ignore = true)
    @Mapping(target = "views",           ignore = true)
    void updateEntity(@MappingTarget DpEntity entity, UpdateDpEntityCmd command);

    @Override List<DpEntityDto> toDtoList(List<DpEntity> entities);
    @Override List<DpEntitySummaryDto> toSummaryDtoList(List<DpEntity> entities);
}
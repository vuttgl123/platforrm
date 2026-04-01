package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.module.*;
import com.meta_forge_platform.platform.domain.entity.DpModule;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DpModuleMapper extends BaseMapper<DpModule, DpModuleDto, DpModuleSummaryDto, CreateDpModuleCmd, UpdateDpModuleCmd> {

    @Override
    DpModuleDto toDto(DpModule entity);

    @Override
    DpModuleSummaryDto toSummaryDto(DpModule entity);

    @Override
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "entities",  ignore = true)
    @Mapping(target = "screens",   ignore = true)
    @Mapping(target = "menus",     ignore = true)
    DpModule toEntity(CreateDpModuleCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "moduleCode", ignore = true)
    @Mapping(target = "createdAt",  ignore = true)
    @Mapping(target = "updatedAt",  ignore = true)
    @Mapping(target = "createdBy",  ignore = true)
    @Mapping(target = "updatedBy",  ignore = true)
    @Mapping(target = "isDeleted",  ignore = true)
    @Mapping(target = "deletedAt",  ignore = true)
    @Mapping(target = "deletedBy",  ignore = true)
    @Mapping(target = "entities",   ignore = true)
    @Mapping(target = "screens",    ignore = true)
    @Mapping(target = "menus",      ignore = true)
    void updateEntity(@MappingTarget DpModule entity, UpdateDpModuleCmd command);

    @Override List<DpModuleDto> toDtoList(List<DpModule> entities);
    @Override List<DpModuleSummaryDto> toSummaryDtoList(List<DpModule> entities);
}
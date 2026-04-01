package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.menu.*;
import com.meta_forge_platform.platform.domain.entity.DpMenu;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {DpModuleMapper.class, DpScreenMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DpMenuMapper extends BaseMapper<DpMenu, DpMenuDto, DpMenuSummaryDto, CreateDpMenuCmd, UpdateDpMenuCmd> {

    @Override
    @Mapping(target = "module",         source = "module")
    @Mapping(target = "screen",         source = "screen")
    @Mapping(target = "parentMenuId",   source = "parentMenu.id")
    @Mapping(target = "parentMenuName", source = "parentMenu.menuName")
    @Mapping(target = "children",       source = "children")
    DpMenuDto toDto(DpMenu entity);

    @Override
    @Mapping(target = "moduleId",     source = "module.id")
    @Mapping(target = "parentMenuId", source = "parentMenu.id")
    @Mapping(target = "screenId",     source = "screen.id")
    DpMenuSummaryDto toSummaryDto(DpMenu entity);

    @Override
    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "module",     ignore = true)
    @Mapping(target = "parentMenu", ignore = true)
    @Mapping(target = "screen",     ignore = true)
    @Mapping(target = "children",   ignore = true)
    @Mapping(target = "createdAt",  ignore = true)
    @Mapping(target = "updatedAt",  ignore = true)
    @Mapping(target = "versionNo",  ignore = true)
    @Mapping(target = "createdBy",  ignore = true)
    @Mapping(target = "updatedBy",  ignore = true)
    @Mapping(target = "isDeleted",  ignore = true)
    @Mapping(target = "deletedAt",  ignore = true)
    @Mapping(target = "deletedBy",  ignore = true)
    DpMenu toEntity(CreateDpMenuCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "menuCode",   ignore = true)
    @Mapping(target = "module",     ignore = true)
    @Mapping(target = "parentMenu", ignore = true)
    @Mapping(target = "screen",     ignore = true)
    @Mapping(target = "children",   ignore = true)
    @Mapping(target = "createdAt",  ignore = true)
    @Mapping(target = "updatedAt",  ignore = true)
    @Mapping(target = "createdBy",  ignore = true)
    @Mapping(target = "updatedBy",  ignore = true)
    @Mapping(target = "isDeleted",  ignore = true)
    @Mapping(target = "deletedAt",  ignore = true)
    @Mapping(target = "deletedBy",  ignore = true)
    void updateEntity(@MappingTarget DpMenu entity, UpdateDpMenuCmd command);

    @Override List<DpMenuDto> toDtoList(List<DpMenu> entities);
    @Override List<DpMenuSummaryDto> toSummaryDtoList(List<DpMenu> entities);
}
package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.screensection.*;
import com.meta_forge_platform.platform.domain.entity.DpScreenSection;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {DpScreenMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DpScreenSectionMapper extends BaseMapper<DpScreenSection, DpScreenSectionDto, DpScreenSectionSummaryDto, CreateDpScreenSectionCmd, UpdateDpScreenSectionCmd> {

    @Override
    @Mapping(target = "screen",            source = "screen")
    @Mapping(target = "parentSectionId",   source = "parentSection.id")
    @Mapping(target = "parentSectionName", source = "parentSection.sectionName")
    DpScreenSectionDto toDto(DpScreenSection entity);

    @Override
    @Mapping(target = "screenId",        source = "screen.id")
    @Mapping(target = "parentSectionId", source = "parentSection.id")
    DpScreenSectionSummaryDto toSummaryDto(DpScreenSection entity);

    @Override
    @Mapping(target = "id",            ignore = true)
    @Mapping(target = "screen",        ignore = true)
    @Mapping(target = "parentSection", ignore = true)
    @Mapping(target = "childSections", ignore = true)
    @Mapping(target = "screenFields",  ignore = true)
    @Mapping(target = "createdAt",     ignore = true)
    @Mapping(target = "updatedAt",     ignore = true)
    @Mapping(target = "versionNo",     ignore = true)
    @Mapping(target = "createdBy",     ignore = true)
    @Mapping(target = "updatedBy",     ignore = true)
    @Mapping(target = "isDeleted",     ignore = true)
    @Mapping(target = "deletedAt",     ignore = true)
    @Mapping(target = "deletedBy",     ignore = true)
    DpScreenSection toEntity(CreateDpScreenSectionCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",            ignore = true)
    @Mapping(target = "sectionCode",   ignore = true)
    @Mapping(target = "screen",        ignore = true)
    @Mapping(target = "parentSection", ignore = true)
    @Mapping(target = "childSections", ignore = true)
    @Mapping(target = "screenFields",  ignore = true)
    @Mapping(target = "createdAt",     ignore = true)
    @Mapping(target = "updatedAt",     ignore = true)
    @Mapping(target = "createdBy",     ignore = true)
    @Mapping(target = "updatedBy",     ignore = true)
    @Mapping(target = "isDeleted",     ignore = true)
    @Mapping(target = "deletedAt",     ignore = true)
    @Mapping(target = "deletedBy",     ignore = true)
    void updateEntity(@MappingTarget DpScreenSection entity, UpdateDpScreenSectionCmd command);

    @Override List<DpScreenSectionDto> toDtoList(List<DpScreenSection> entities);
    @Override List<DpScreenSectionSummaryDto> toSummaryDtoList(List<DpScreenSection> entities);
}
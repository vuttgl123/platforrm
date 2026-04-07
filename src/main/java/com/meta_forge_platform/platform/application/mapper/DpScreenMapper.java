package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.screen.CreateDpScreenCmd;
import com.meta_forge_platform.platform.application.dto.screen.DpScreenDto;
import com.meta_forge_platform.platform.application.dto.screen.DpScreenSummaryDto;
import com.meta_forge_platform.platform.application.dto.screen.UpdateDpScreenCmd;
import com.meta_forge_platform.platform.domain.entity.DpScreen;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {DpModuleMapper.class, DpEntityMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface DpScreenMapper extends BaseMapper<
        DpScreen,
        DpScreenDto,
        DpScreenSummaryDto,
        CreateDpScreenCmd,
        UpdateDpScreenCmd> {

    @Override
    @Mapping(target = "module", source = "module")
    @Mapping(target = "entity", source = "entity")
    DpScreenDto toDto(DpScreen entity);

    @Override
    @Mapping(target = "moduleId", source = "module.id")
    @Mapping(target = "entityId", source = "entity.id")
    DpScreenSummaryDto toSummaryDto(DpScreen entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "entity", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    DpScreen toEntity(CreateDpScreenCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "screenCode", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "entity", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(@MappingTarget DpScreen entity, UpdateDpScreenCmd command);

    @Override
    List<DpScreenDto> toDtoList(List<DpScreen> entities);

    @Override
    List<DpScreenSummaryDto> toSummaryDtoList(List<DpScreen> entities);
}
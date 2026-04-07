package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.view.CreateDpViewCmd;
import com.meta_forge_platform.platform.application.dto.view.DpViewDto;
import com.meta_forge_platform.platform.application.dto.view.DpViewSummaryDto;
import com.meta_forge_platform.platform.application.dto.view.UpdateDpViewCmd;
import com.meta_forge_platform.platform.domain.entity.DpView;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {DpEntityMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface DpViewMapper extends BaseMapper<
        DpView,
        DpViewDto,
        DpViewSummaryDto,
        CreateDpViewCmd,
        UpdateDpViewCmd> {

    @Override
    @Mapping(target = "entity", source = "entity")
    DpViewDto toDto(DpView entity);

    @Override
    @Mapping(target = "entityId", source = "entity.id")
    DpViewSummaryDto toSummaryDto(DpView entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entity", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    DpView toEntity(CreateDpViewCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "viewCode", ignore = true)
    @Mapping(target = "entity", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(@MappingTarget DpView entity, UpdateDpViewCmd command);

    @Override
    List<DpViewDto> toDtoList(List<DpView> entities);

    @Override
    List<DpViewSummaryDto> toSummaryDtoList(List<DpView> entities);
}
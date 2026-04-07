package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.fieldoption.CreateDpFieldOptionCmd;
import com.meta_forge_platform.platform.application.dto.fieldoption.DpFieldOptionDto;
import com.meta_forge_platform.platform.application.dto.fieldoption.DpFieldOptionSummaryDto;
import com.meta_forge_platform.platform.application.dto.fieldoption.UpdateDpFieldOptionCmd;
import com.meta_forge_platform.platform.domain.entity.DpFieldOption;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {DpFieldMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface DpFieldOptionMapper extends BaseMapper<
        DpFieldOption,
        DpFieldOptionDto,
        DpFieldOptionSummaryDto,
        CreateDpFieldOptionCmd,
        UpdateDpFieldOptionCmd> {

    @Override
    @Mapping(target = "field", source = "field")
    DpFieldOptionDto toDto(DpFieldOption entity);

    @Override
    DpFieldOptionSummaryDto toSummaryDto(DpFieldOption entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "field", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    DpFieldOption toEntity(CreateDpFieldOptionCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "optionCode", ignore = true)
    @Mapping(target = "field", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(@MappingTarget DpFieldOption entity, UpdateDpFieldOptionCmd command);

    @Override
    List<DpFieldOptionDto> toDtoList(List<DpFieldOption> entities);

    @Override
    List<DpFieldOptionSummaryDto> toSummaryDtoList(List<DpFieldOption> entities);
}
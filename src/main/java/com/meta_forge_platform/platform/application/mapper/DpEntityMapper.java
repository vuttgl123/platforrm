package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.entity.DpEntityDto;
import com.meta_forge_platform.platform.application.dto.entity.DpEntitySummaryDto;
import com.meta_forge_platform.platform.domain.entity.DpEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {DpModuleMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface DpEntityMapper {

    @Mapping(target = "module", source = "module")
    DpEntityDto toDto(DpEntity entity);

    @Mapping(target = "moduleId", source = "module.id")
    @Mapping(target = "moduleName", source = "module.moduleName")
    DpEntitySummaryDto toSummaryDto(DpEntity entity);

    List<DpEntityDto> toDtoList(List<DpEntity> entities);

    List<DpEntitySummaryDto> toSummaryDtoList(List<DpEntity> entities);
}
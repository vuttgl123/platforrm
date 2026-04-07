package com.meta_forge_platform.platform.application.mapper;

import com.meta_forge_platform.platform.application.dto.entityconstraint.CreateDpEntityConstraintCmd;
import com.meta_forge_platform.platform.application.dto.entityconstraint.DpEntityConstraintDto;
import com.meta_forge_platform.platform.application.dto.entityconstraint.DpEntityConstraintSummaryDto;
import com.meta_forge_platform.platform.application.dto.entityconstraint.UpdateDpEntityConstraintCmd;
import com.meta_forge_platform.platform.domain.entity.DpEntityConstraint;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {DpEntityMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface DpEntityConstraintMapper extends BaseMapper<
        DpEntityConstraint,
        DpEntityConstraintDto,
        DpEntityConstraintSummaryDto,
        CreateDpEntityConstraintCmd,
        UpdateDpEntityConstraintCmd> {

    @Override
    @Mapping(target = "entity", source = "entity")
    DpEntityConstraintDto toDto(DpEntityConstraint entity);

    @Override
    @Mapping(target = "entityId", source = "entity.id")
    DpEntityConstraintSummaryDto toSummaryDto(DpEntityConstraint entity);

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
    DpEntityConstraint toEntity(CreateDpEntityConstraintCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "constraintCode", ignore = true)
    @Mapping(target = "entity", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(@MappingTarget DpEntityConstraint entity, UpdateDpEntityConstraintCmd command);

    @Override
    List<DpEntityConstraintDto> toDtoList(List<DpEntityConstraint> entities);

    @Override
    List<DpEntityConstraintSummaryDto> toSummaryDtoList(List<DpEntityConstraint> entities);
}
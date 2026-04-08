package com.meta_forge_platform.runtime.application.mapper;

import com.meta_forge_platform.runtime.application.dto.recordvalue.*;
import com.meta_forge_platform.runtime.domain.entity.AppRecordValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppRecordValueMapper {

    @Mapping(target = "recordId", source = "record.id")
    @Mapping(target = "fieldId", source = "field.id")
    AppRecordValueDto toDto(AppRecordValue entity);

    List<AppRecordValueDto> toDtoList(List<AppRecordValue> entities);

    default AppRecordValueSummaryDto toSummaryDto(AppRecordValue entity) {
        Object value = entity.getValueString();
        if (entity.getValueInteger() != null) value = entity.getValueInteger();
        if (entity.getValueDecimal() != null) value = entity.getValueDecimal();
        if (entity.getValueBoolean() != null) value = entity.getValueBoolean();
        if (entity.getValueDate() != null) value = entity.getValueDate();
        if (entity.getValueDatetime() != null) value = entity.getValueDatetime();
        if (entity.getValueJson() != null) value = entity.getValueJson();

        return AppRecordValueSummaryDto.builder()
                .id(entity.getId())
                .recordId(entity.getRecord().getId())
                .fieldId(entity.getField().getId())
                .seqNo(entity.getSeqNo())
                .value(value)
                .build();
    }

    List<AppRecordValueSummaryDto> toSummaryDtoList(List<AppRecordValue> entities);
}
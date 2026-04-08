package com.meta_forge_platform.runtime.application.mapper;

import com.meta_forge_platform.runtime.application.dto.recordlink.AppRecordLinkDto;
import com.meta_forge_platform.runtime.application.dto.recordlink.AppRecordLinkSummaryDto;
import com.meta_forge_platform.runtime.domain.entity.AppRecordLink;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppRecordLinkMapper {

    @Mapping(target = "relationId", source = "relation.id")
    @Mapping(target = "relationCode", source = "relation.relationCode")
    @Mapping(target = "relationName", source = "relation.relationName")
    @Mapping(target = "sourceRecordId", source = "sourceRecord.id")
    @Mapping(target = "sourceRecordCode", source = "sourceRecord.recordCode")
    @Mapping(target = "sourceDisplayName", source = "sourceRecord.displayName")
    @Mapping(target = "fieldId", source = "field.id")
    @Mapping(target = "fieldCode", source = "field.fieldCode")
    @Mapping(target = "fieldName", source = "field.fieldName")
    @Mapping(target = "targetRecordId", source = "targetRecord.id")
    @Mapping(target = "targetRecordCode", source = "targetRecord.recordCode")
    @Mapping(target = "targetDisplayName", source = "targetRecord.displayName")
    @Mapping(target = "linkType", source = "linkType")
    AppRecordLinkDto toDto(AppRecordLink entity);

    @Mapping(target = "sourceRecordId", source = "sourceRecord.id")
    @Mapping(target = "fieldId", source = "field.id")
    @Mapping(target = "targetRecordId", source = "targetRecord.id")
    @Mapping(target = "targetRecordCode", source = "targetRecord.recordCode")
    @Mapping(target = "targetDisplayName", source = "targetRecord.displayName")
    @Mapping(target = "linkType", source = "linkType")
    AppRecordLinkSummaryDto toSummaryDto(AppRecordLink entity);

    List<AppRecordLinkDto> toDtoList(List<AppRecordLink> entities);

    List<AppRecordLinkSummaryDto> toSummaryDtoList(List<AppRecordLink> entities);
}
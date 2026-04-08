package com.meta_forge_platform.runtime.application.mapper;

import com.meta_forge_platform.runtime.application.dto.recordblob.AppRecordBlobDto;
import com.meta_forge_platform.runtime.application.dto.recordblob.AppRecordBlobSummaryDto;
import com.meta_forge_platform.runtime.domain.entity.AppRecordBlob;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppRecordBlobMapper {

    @Mapping(target = "recordId", source = "record.id")
    @Mapping(target = "recordCode", source = "record.recordCode")
    @Mapping(target = "recordDisplayName", source = "record.displayName")
    @Mapping(target = "fieldId", source = "field.id")
    @Mapping(target = "fieldCode", source = "field.fieldCode")
    @Mapping(target = "fieldName", source = "field.fieldName")
    @Mapping(target = "blobId", source = "blob.id")
    @Mapping(target = "blobCode", source = "blob.blobCode")
    @Mapping(target = "fileName", source = "blob.fileName")
    @Mapping(target = "contentType", source = "blob.contentType")
    @Mapping(target = "fileSize", source = "blob.fileSize")
    AppRecordBlobDto toDto(AppRecordBlob entity);

    @Mapping(target = "recordId", source = "record.id")
    @Mapping(target = "fieldId", source = "field.id")
    @Mapping(target = "blobId", source = "blob.id")
    @Mapping(target = "fileName", source = "blob.fileName")
    @Mapping(target = "contentType", source = "blob.contentType")
    AppRecordBlobSummaryDto toSummaryDto(AppRecordBlob entity);

    List<AppRecordBlobDto> toDtoList(List<AppRecordBlob> entities);

    List<AppRecordBlobSummaryDto> toSummaryDtoList(List<AppRecordBlob> entities);
}
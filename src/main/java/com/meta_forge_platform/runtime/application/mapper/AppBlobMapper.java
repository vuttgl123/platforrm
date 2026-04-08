package com.meta_forge_platform.runtime.application.mapper;

import com.meta_forge_platform.runtime.application.dto.blob.AppBlobDto;
import com.meta_forge_platform.runtime.application.dto.blob.AppBlobSummaryDto;
import com.meta_forge_platform.runtime.application.dto.blob.CreateAppBlobCmd;
import com.meta_forge_platform.runtime.application.dto.blob.UpdateAppBlobCmd;
import com.meta_forge_platform.runtime.domain.entity.AppBlob;
import com.meta_forge_platform.shared.application.mapper.BaseMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.text.DecimalFormat;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface AppBlobMapper extends BaseMapper<
        AppBlob,
        AppBlobDto,
        AppBlobSummaryDto,
        CreateAppBlobCmd,
        UpdateAppBlobCmd> {

    @Override
    @Mapping(target = "fileSizeFormatted", expression = "java(formatFileSize(entity.getFileSize()))")
    @Mapping(target = "metadataJson", source = "metadata")
    @Mapping(target = "storageProvider", expression = "java(entity.getStorageProvider() != null ? entity.getStorageProvider().name() : null)")
    AppBlobDto toDto(AppBlob entity);

    @Override
    @Mapping(target = "fileSizeFormatted", expression = "java(formatFileSize(entity.getFileSize()))")
    @Mapping(target = "isImage", expression = "java(entity.isImage())")
    AppBlobSummaryDto toSummaryDto(AppBlob entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "blobCode", ignore = true)
    @Mapping(target = "contentType", ignore = true)
    @Mapping(target = "fileSize", ignore = true)
    @Mapping(target = "storageProvider", ignore = true)
    @Mapping(target = "storageKey", ignore = true)
    @Mapping(target = "checksum", ignore = true)
    @Mapping(target = "metadata", ignore = true)
    AppBlob toEntity(CreateAppBlobCmd command);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blobCode", ignore = true)
    @Mapping(target = "contentType", ignore = true)
    @Mapping(target = "fileSize", ignore = true)
    @Mapping(target = "storageKey", ignore = true)
    @Mapping(target = "storageProvider", ignore = true)
    @Mapping(target = "checksum", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "versionNo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "metadata", ignore = true)
    void updateEntity(@MappingTarget AppBlob entity, UpdateAppBlobCmd command);

    @Override
    List<AppBlobDto> toDtoList(List<AppBlob> entities);

    @Override
    List<AppBlobSummaryDto> toSummaryDtoList(List<AppBlob> entities);

    default String formatFileSize(Long fileSize) {
        if (fileSize == null) {
            return null;
        }

        double size = fileSize;
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;

        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }

        DecimalFormat df = size % 1 == 0
                ? new DecimalFormat("#")
                : new DecimalFormat("#.##");

        return df.format(size) + " " + units[unitIndex];
    }
}
package com.meta_forge_platform.runtime.application.dto.blob;

import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class AppBlobDto extends BaseDto {
    private String blobCode;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private String fileSizeFormatted;
    private String storageProvider;
    private String storageKey;
    private String checksum;
    private Map<String, Object> metadataJson;
    private Boolean isDeleted;
}
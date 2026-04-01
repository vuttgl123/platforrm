package com.meta_forge_platform.runtime.application.dto.blob;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AppBlobSummaryDto {
    private Long id;
    private String blobCode;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private String fileSizeFormatted;
    private Boolean isImage;
}
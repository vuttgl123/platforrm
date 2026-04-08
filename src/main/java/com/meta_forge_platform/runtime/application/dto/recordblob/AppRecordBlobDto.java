package com.meta_forge_platform.runtime.application.dto.recordblob;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRecordBlobDto {

    private Long id;

    private Long recordId;
    private String recordCode;
    private String recordDisplayName;

    private Long fieldId;
    private String fieldCode;
    private String fieldName;

    private Long blobId;
    private String blobCode;
    private String fileName;
    private String contentType;
    private Long fileSize;

    private Integer sortOrder;

    private Long versionNo;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
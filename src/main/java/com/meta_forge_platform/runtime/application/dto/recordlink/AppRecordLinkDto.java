package com.meta_forge_platform.runtime.application.dto.recordlink;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRecordLinkDto {

    private Long id;

    private Long relationId;
    private String relationCode;
    private String relationName;

    private Long sourceRecordId;
    private String sourceRecordCode;
    private String sourceDisplayName;

    private Long fieldId;
    private String fieldCode;
    private String fieldName;

    private Long targetRecordId;
    private String targetRecordCode;
    private String targetDisplayName;

    private String linkType;
    private Integer sortOrder;

    private Long versionNo;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
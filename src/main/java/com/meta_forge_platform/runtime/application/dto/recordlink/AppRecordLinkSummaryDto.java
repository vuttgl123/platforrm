package com.meta_forge_platform.runtime.application.dto.recordlink;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRecordLinkSummaryDto {

    private Long id;
    private Long sourceRecordId;
    private Long fieldId;
    private Long targetRecordId;
    private String targetRecordCode;
    private String targetDisplayName;
    private String linkType;
    private Integer sortOrder;
}
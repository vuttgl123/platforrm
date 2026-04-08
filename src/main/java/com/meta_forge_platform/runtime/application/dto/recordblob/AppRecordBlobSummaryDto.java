package com.meta_forge_platform.runtime.application.dto.recordblob;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRecordBlobSummaryDto {

    private Long id;

    private Long recordId;
    private Long fieldId;
    private Long blobId;

    private String fileName;
    private String contentType;
    private Integer sortOrder;
}
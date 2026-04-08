package com.meta_forge_platform.runtime.application.dto.recordvalue;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRecordValueSummaryDto {

    private Long id;
    private Long recordId;
    private Long fieldId;
    private Integer seqNo;
    private Object value;
}
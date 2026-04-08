package com.meta_forge_platform.runtime.application.dto.recordvalue;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRecordValueDto {

    private Long id;

    private Long recordId;
    private Long fieldId;

    private Integer seqNo;

    private String valueString;
    private String valueText;
    private Long valueInteger;
    private BigDecimal valueDecimal;
    private Boolean valueBoolean;
    private LocalDate valueDate;
    private LocalDateTime valueDatetime;
    private String valueJson;
}
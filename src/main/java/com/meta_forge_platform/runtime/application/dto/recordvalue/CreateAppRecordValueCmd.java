package com.meta_forge_platform.runtime.application.dto.recordvalue;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAppRecordValueCmd {

    @NotNull
    private Long recordId;

    @NotNull
    private Long fieldId;

    @Builder.Default
    private Integer seqNo = 0;

    private String valueString;
    private String valueText;
    private Long valueInteger;
    private String valueJson;
}
package com.meta_forge_platform.runtime.application.dto.recordvalue;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAppRecordValueCmd {

    private String valueString;
    private String valueText;
    private Long valueInteger;
    private String valueJson;

    @NotNull
    private Long versionNo;
}
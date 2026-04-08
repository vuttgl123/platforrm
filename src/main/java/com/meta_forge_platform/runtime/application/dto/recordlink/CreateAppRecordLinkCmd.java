package com.meta_forge_platform.runtime.application.dto.recordlink;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAppRecordLinkCmd {

    private Long relationId;

    @NotNull
    private Long sourceRecordId;

    private Long fieldId;

    @NotNull
    private Long targetRecordId;

    @NotBlank
    @Size(max = 30)
    private String linkType;

    @Builder.Default
    @NotNull
    private Integer sortOrder = 0;
}
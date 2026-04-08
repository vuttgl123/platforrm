package com.meta_forge_platform.runtime.application.dto.recordlink;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAppRecordLinkCmd {

    private Long relationId;

    private Long fieldId;

    @NotBlank
    @Size(max = 30)
    private String linkType;

    @NotNull
    private Integer sortOrder;

    @NotNull
    private Long versionNo;
}
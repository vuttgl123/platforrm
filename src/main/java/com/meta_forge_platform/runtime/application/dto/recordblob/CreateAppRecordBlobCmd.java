package com.meta_forge_platform.runtime.application.dto.recordblob;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAppRecordBlobCmd {

    @NotNull
    private Long recordId;

    @NotNull
    private Long fieldId;

    @NotNull
    private Long blobId;

    @Builder.Default
    @NotNull
    private Integer sortOrder = 0;
}
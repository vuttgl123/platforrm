package com.meta_forge_platform.runtime.application.dto.recordblob;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAppRecordBlobCmd {

    @NotNull
    private Integer sortOrder;

    @NotNull
    private Long versionNo;
}
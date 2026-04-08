package com.meta_forge_platform.runtime.application.dto.recordstatehistory;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAppRecordStateHistoryCmd {

    private String note;

    @NotNull
    private Long versionNo;
}
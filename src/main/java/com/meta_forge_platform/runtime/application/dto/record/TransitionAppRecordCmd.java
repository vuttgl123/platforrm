package com.meta_forge_platform.runtime.application.dto.record;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TransitionAppRecordCmd {

    @NotNull(message = "toStateId không được để trống")
    private Long toStateId;

    private String actionCode;
    private String note;

    @NotNull(message = "versionNo bắt buộc")
    private Long versionNo;
}
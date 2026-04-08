package com.meta_forge_platform.runtime.application.dto.record;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAppRecordCmd {

    @Size(max = 500)
    private String displayName;

    @NotBlank
    @Size(max = 30)
    private String status;

    private Map<String, Object> dataJson;

    @NotNull
    private Long versionNo;
}
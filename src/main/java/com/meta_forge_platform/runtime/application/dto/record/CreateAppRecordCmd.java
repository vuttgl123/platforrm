package com.meta_forge_platform.runtime.application.dto.record;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAppRecordCmd {

    @NotNull
    private Long entityId;

    @Size(max = 100)
    private String recordCode;

    @Size(max = 500)
    private String displayName;

    private Long parentRecordId;

    private Long rootRecordId;

    @Builder.Default
    @NotBlank
    @Size(max = 30)
    private String status = "ACTIVE";

    private Map<String, Object> dataJson;
}
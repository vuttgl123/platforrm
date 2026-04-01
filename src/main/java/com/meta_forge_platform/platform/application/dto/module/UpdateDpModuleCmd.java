package com.meta_forge_platform.platform.application.dto.module;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDpModuleCmd {
    @NotBlank @Size(max = 255) private String moduleName;
    private String description;
    private String status;
    private Integer sortOrder;
    @NotNull(message = "versionNo bắt buộc khi cập nhật") private Long versionNo;
}
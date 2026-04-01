package com.meta_forge_platform.platform.application.dto.fieldgroup;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDpFieldGroupCmd {
    @NotBlank @Size(max = 255) private String groupName;
    private Integer sortOrder;
    private Map<String, Object> configJson;
    @NotNull private Long versionNo;
}
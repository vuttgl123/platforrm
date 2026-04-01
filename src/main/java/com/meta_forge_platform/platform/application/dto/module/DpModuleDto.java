package com.meta_forge_platform.platform.application.dto.module;

import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpModuleDto extends BaseDto {
    private String moduleCode;
    private String moduleName;
    private String description;
    private String status;
    private Integer sortOrder;
    private Boolean isSystem;
    private Boolean isDeleted;
}
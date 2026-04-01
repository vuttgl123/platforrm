package com.meta_forge_platform.platform.application.dto.fieldgroup;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpFieldGroupSummaryDto {
    private Long id;
    private String groupCode;
    private String groupName;
    private Integer sortOrder;
    private Long entityId;
}
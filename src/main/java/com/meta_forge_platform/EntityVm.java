package com.meta_forge_platform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * View Model cho Entity Admin UI
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityVm {

    private Long id;

    private String code;

    private String name;

    private String moduleCode;

    /**
     * DRAFT / PUBLISHED
     */
    private String status;

    private String description;
}
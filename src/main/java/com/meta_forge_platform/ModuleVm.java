package com.meta_forge_platform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * View Model cho Module Admin UI (temporary)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleVm {

    private Long id;

    private String code;

    private String name;

    private String description;

    /**
     * Dùng string để match với FTL (true/false)
     */
    private String active;

    private String createdAt;
}
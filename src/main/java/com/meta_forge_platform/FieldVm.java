package com.meta_forge_platform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * View Model cho Field Admin UI
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldVm {

    private Long id;

    private String code;

    private String label;

    private String entityCode;

    /**
     * TEXT, NUMBER, EMAIL, DATE...
     */
    private String fieldType;

    /**
     * dùng String để render badge trong FTL
     */
    private String required;

    private String sortable;

    private Integer orderNo;

    private String defaultValue;
}
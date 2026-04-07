package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.platform.domain.enumeration.ScreenSectionType;
import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import com.meta_forge_platform.shared.infrastructure.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "dp_screen_section",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_screen_section_code",
                columnNames = {"screen_id", "section_code"}
        )
)
public class DpScreenSection extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "screen_id", nullable = false)
    private DpScreen screen;

    @Column(name = "section_code", nullable = false, length = 100)
    private String sectionCode;

    @Column(name = "section_name", nullable = false, length = 255)
    private String sectionName;

    @Enumerated(EnumType.STRING)
    @Column(name = "section_type", nullable = false, length = 50)
    private ScreenSectionType sectionType;

    @Column(name = "parent_section_id")
    private Long parentSectionId;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> config;

    public static DpScreenSection create(
            DpScreen screen,
            String code,
            String name,
            ScreenSectionType type
    ) {
        DpScreenSection section = new DpScreenSection();
        section.screen = screen;
        section.sectionCode = code;
        section.sectionName = name;
        section.sectionType = type;
        section.sortOrder = 0;
        return section;
    }

    public void assignParent(Long parentSectionId) {
        this.parentSectionId = parentSectionId;
    }

    public void updateBasic(String name, ScreenSectionType type, Integer sortOrder) {
        this.sectionName = name;
        this.sectionType = type;
        this.sortOrder = sortOrder;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, getId());
        }
        softDelete(deletedBy);
    }
}
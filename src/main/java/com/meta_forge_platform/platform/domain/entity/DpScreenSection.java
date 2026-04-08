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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_section_id")
    private DpScreenSection parentSection;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> config;

    @Version
    @Column(name = "version_no", nullable = false)
    private Long versionNo;

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

    public void applyMetadata(
            String sectionName,
            ScreenSectionType sectionType,
            DpScreenSection parentSection,
            Integer sortOrder,
            Map<String, Object> config
    ) {
        this.sectionName = sectionName;
        this.sectionType = sectionType;
        this.parentSection = parentSection;
        this.sortOrder = sortOrder;
        this.config = config;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "DpScreenSection", getId());
        }
        softDelete(deletedBy);
    }
}
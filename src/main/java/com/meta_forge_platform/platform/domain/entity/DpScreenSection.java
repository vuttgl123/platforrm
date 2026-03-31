package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.infrastructure.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dp_screen_section",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_screen_section_code",
                columnNames = {"screen_id", "section_code"}))
@SQLDelete(sql = "UPDATE dp_screen_section SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class DpScreenSection extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "screen_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_screen_section_screen"))
    private DpScreen screen;

    @Column(name = "section_code", nullable = false, length = 100)
    private String sectionCode;

    @Column(name = "section_name", nullable = false, length = 255)
    private String sectionName;

    @Column(name = "section_type", nullable = false, length = 50)
    private String sectionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_section_id", foreignKey = @ForeignKey(name = "fk_dp_screen_section_parent"))
    private DpScreenSection parentSection;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    private Integer sortOrder = 0;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> configJson;

    @OneToMany(mappedBy = "parentSection", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DpScreenSection> childSections = new ArrayList<>();

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DpScreenField> screenFields = new ArrayList<>();
}
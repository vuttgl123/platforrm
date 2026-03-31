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
@Table(name = "dp_entity",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_entity_module_code",
                columnNames = {"module_id", "entity_code"}))
@SQLDelete(sql = "UPDATE dp_entity SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class DpEntity extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "module_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_entity_module"))
    private DpModule module;

    @Column(name = "entity_code", nullable = false, length = 100)
    private String entityCode;

    @Column(name = "entity_name", nullable = false, length = 255)
    private String entityName;

    @Column(name = "table_strategy", nullable = false, length = 30)
    @Builder.Default
    private String tableStrategy = "GENERIC";

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_root", nullable = false)
    @Builder.Default
    private Boolean isRoot = true;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "display_name_pattern", length = 500)
    private String displayNamePattern;

    @Convert(converter = JsonConverter.ListConverter.class)
    @Column(name = "default_sort_json", columnDefinition = "JSON")
    private List<Object> defaultSortJson;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> configJson;

    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DpFieldGroup> fieldGroups = new ArrayList<>();

    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DpField> fields = new ArrayList<>();

    @OneToMany(mappedBy = "sourceEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DpEntityRelation> sourceRelations = new ArrayList<>();

    @OneToMany(mappedBy = "targetEntity")
    @Builder.Default
    private List<DpEntityRelation> targetRelations = new ArrayList<>();

    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DpEntityConstraint> constraints = new ArrayList<>();

    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DpWorkflow> workflows = new ArrayList<>();

    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DpView> views = new ArrayList<>();
}
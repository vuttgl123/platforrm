package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.platform.domain.enumeration.GroupLayoutType;
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
@Table(name = "dp_field_group",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_field_group_entity_code",
                columnNames = {"entity_id", "group_code"}))
public class DpFieldGroup extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false)
    private DpEntity entity;

    @Column(name = "group_code", nullable = false, length = 100)
    private String groupCode;

    @Column(name = "group_name", nullable = false, length = 255)
    private String groupName;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "layout_type", nullable = false)
    private GroupLayoutType layoutType;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> config;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public static DpFieldGroup create(DpEntity entity, String code, String name) {
        DpFieldGroup g = new DpFieldGroup();
        g.entity = entity;
        g.groupCode = code;
        g.groupName = name;
        g.sortOrder = 0;
        g.layoutType = GroupLayoutType.VERTICAL;
        g.isActive = true;
        return g;
    }

    public void applyMetadata(
            String name,
            Integer sortOrder,
            GroupLayoutType layoutType,
            Map<String, Object> config,
            Boolean isActive
    ) {
        this.groupName = name;
        this.sortOrder = sortOrder;
        this.layoutType = layoutType;
        this.config = config;
        this.isActive = isActive;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "DpFieldGroup", getId());
        }
        softDelete(deletedBy);
    }
}
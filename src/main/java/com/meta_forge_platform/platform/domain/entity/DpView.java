package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.platform.domain.enumeration.ViewType;
import com.meta_forge_platform.platform.domain.enumeration.ViewVisibility;
import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import com.meta_forge_platform.shared.infrastructure.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "dp_view",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_view_entity_code",
                columnNames = {"entity_id", "view_code"}
        )
)
public class DpView extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false)
    private DpEntity entity;

    @Column(name = "view_code", nullable = false, length = 100)
    private String viewCode;

    @Column(name = "view_name", nullable = false, length = 255)
    private String viewName;

    @Enumerated(EnumType.STRING)
    @Column(name = "view_type", nullable = false, length = 30)
    private ViewType viewType;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "query_json", nullable = false, columnDefinition = "JSON")
    private Map<String, Object> query;

    @Convert(converter = JsonConverter.ListConverter.class)
    @Column(name = "columns_json", columnDefinition = "JSON")
    private List<Object> columns;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false, length = 30)
    private ViewVisibility visibility;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Version
    @Column(name = "version_no", nullable = false)
    private Long versionNo;

    public static DpView create(
            DpEntity entity,
            String code,
            String name,
            ViewType type,
            Map<String, Object> query,
            List<Object> columns
    ) {
        DpView view = new DpView();
        view.entity = entity;
        view.viewCode = code;
        view.viewName = name;
        view.viewType = type;
        view.query = query;
        view.columns = columns;
        view.visibility = ViewVisibility.MODULE;
        view.sortOrder = 0;
        view.isDefault = false;
        view.isActive = true;
        return view;
    }

    public void applyMetadata(
            String viewName,
            ViewType viewType,
            Map<String, Object> query,
            List<Object> columns,
            ViewVisibility visibility,
            Integer sortOrder,
            Boolean isDefault,
            Boolean isActive
    ) {
        this.viewName = viewName;
        this.viewType = viewType;
        this.query = query;
        this.columns = columns;
        this.visibility = visibility;
        this.sortOrder = sortOrder;
        this.isDefault = isDefault;
        this.isActive = isActive;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "DpView", getId());
        }
        softDelete(deletedBy);
    }
}
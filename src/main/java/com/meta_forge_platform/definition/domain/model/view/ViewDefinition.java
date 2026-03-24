package com.meta_forge_platform.definition.domain.model.view;

import com.meta_forge_platform.definition.domain.model.entity.EntityDefinition;
import com.meta_forge_platform.shared.domain.base.BaseVersionedActivableSortableEntity;
import com.meta_forge_platform.shared.domain.enumtype.ViewType;
import com.meta_forge_platform.shared.domain.exception.ValidationException;
import com.meta_forge_platform.shared.infrastructure.converter.JsonMapConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "dp_view",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_dp_view_entity_code", columnNames = {"entity_id", "view_code"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ViewDefinition extends BaseVersionedActivableSortableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_view_entity"))
    private EntityDefinition entity;

    @Column(name = "view_code", nullable = false, length = 100)
    private String code;

    @Column(name = "view_name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "view_type", nullable = false, length = 30)
    private ViewType viewType;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "query_json", columnDefinition = "json")
    private Map<String, Object> query = new LinkedHashMap<>();

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "columns_json", columnDefinition = "json")
    private Map<String, Object> columns = new LinkedHashMap<>();

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "config_json", columnDefinition = "json")
    private Map<String, Object> config = new LinkedHashMap<>();

    public ViewDefinition(EntityDefinition entity, String code, String name, ViewType viewType) {
        changeEntity(entity);
        changeCode(code);
        changeName(name);
        changeViewType(viewType);
    }

    public void changeEntity(EntityDefinition entity) {
        if (entity == null) {
            throw new ValidationException("Entity must not be null");
        }
        this.entity = entity;
    }

    public void changeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("View code must not be blank");
        }
        this.code = code.trim();
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("View name must not be blank");
        }
        this.name = name.trim();
    }

    public void changeViewType(ViewType viewType) {
        if (viewType == null) {
            throw new ValidationException("View type must not be null");
        }
        this.viewType = viewType;
    }

    public void changeQuery(Map<String, Object> query) {
        this.query = query == null ? new LinkedHashMap<>() : new LinkedHashMap<>(query);
    }

    public void changeColumns(Map<String, Object> columns) {
        this.columns = columns == null ? new LinkedHashMap<>() : new LinkedHashMap<>(columns);
    }

    public void changeConfig(Map<String, Object> config) {
        this.config = config == null ? new LinkedHashMap<>() : new LinkedHashMap<>(config);
    }
}

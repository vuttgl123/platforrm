package com.meta_forge_platform.definition.domain.model.screen;

import com.meta_forge_platform.definition.domain.model.entity.EntityDefinition;
import com.meta_forge_platform.shared.domain.base.BaseVersionedActivableSortableEntity;
import com.meta_forge_platform.shared.domain.enumtype.ScreenType;
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
        name = "dp_screen",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_dp_screen_entity_code", columnNames = {"entity_id", "screen_code"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ScreenDefinition extends BaseVersionedActivableSortableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_screen_entity"))
    private EntityDefinition entity;

    @Column(name = "screen_code", nullable = false, length = 100)
    private String code;

    @Column(name = "screen_name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "screen_type", nullable = false, length = 30)
    private ScreenType screenType;

    @Column(name = "title_expr", length = 500)
    private String titleExpression;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "layout_json", columnDefinition = "json")
    private Map<String, Object> layout = new LinkedHashMap<>();

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "config_json", columnDefinition = "json")
    private Map<String, Object> config = new LinkedHashMap<>();

    public ScreenDefinition(EntityDefinition entity, String code, String name, ScreenType screenType) {
        changeEntity(entity);
        changeCode(code);
        changeName(name);
        changeScreenType(screenType);
    }

    public void changeEntity(EntityDefinition entity) {
        if (entity == null) {
            throw new ValidationException("Entity must not be null");
        }
        this.entity = entity;
    }

    public void changeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("Screen code must not be blank");
        }
        this.code = code.trim();
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Screen name must not be blank");
        }
        this.name = name.trim();
    }

    public void changeScreenType(ScreenType screenType) {
        if (screenType == null) {
            throw new ValidationException("Screen type must not be null");
        }
        this.screenType = screenType;
    }

    public void changeTitleExpression(String titleExpression) {
        this.titleExpression = titleExpression;
    }

    public void changeLayout(Map<String, Object> layout) {
        this.layout = layout == null ? new LinkedHashMap<>() : new LinkedHashMap<>(layout);
    }

    public void changeConfig(Map<String, Object> config) {
        this.config = config == null ? new LinkedHashMap<>() : new LinkedHashMap<>(config);
    }
}

package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.platform.domain.enumeration.ScreenType;
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
@Table(name = "dp_screen",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_screen_module_code",
                columnNames = {"module_id", "screen_code"}))
public class DpScreen extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "module_id", nullable = false)
    private DpModule module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id")
    private DpEntity entity;

    @Column(name = "screen_code", nullable = false, length = 100)
    private String screenCode;

    @Column(name = "screen_name", nullable = false, length = 255)
    private String screenName;

    @Enumerated(EnumType.STRING)
    @Column(name = "screen_type", nullable = false)
    private ScreenType screenType;

    @Column(name = "route_path", length = 255)
    private String routePath;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "schema_json", columnDefinition = "JSON")
    private Map<String, Object> schema;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> config;

    public static DpScreen create(
            DpModule module,
            DpEntity entity,
            String code,
            String name,
            ScreenType type
    ) {
        DpScreen s = new DpScreen();
        s.module = module;
        s.entity = entity;
        s.screenCode = code;
        s.screenName = name;
        s.screenType = type;
        s.isActive = true;
        s.sortOrder = 0;
        return s;
    }

    public void updateBasic(String name, String route) {
        this.screenName = name;
        this.routePath = route;
    }

    public void updateSchema(Map<String, Object> schema) {
        this.schema = schema;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, getId());
        }
        softDelete(deletedBy);
    }
}
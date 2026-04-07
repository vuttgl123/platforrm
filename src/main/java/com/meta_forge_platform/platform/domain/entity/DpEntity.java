package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import com.meta_forge_platform.shared.infrastructure.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "dp_entity",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_entity_module_code",
                columnNames = {"module_id", "entity_code"}
        )
)
public class DpEntity extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "module_id", nullable = false)
    private DpModule module;

    @Column(name = "entity_code", nullable = false, length = 100)
    private String entityCode;

    @Column(name = "entity_name", nullable = false, length = 255)
    private String entityName;

    @Column(name = "table_strategy", nullable = false, length = 30)
    private String tableStrategy;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_root", nullable = false)
    private Boolean isRoot;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Version
    @Column(name = "version_no", nullable = false)
    private Long versionNo;

    @Column(name = "display_name_pattern", length = 500)
    private String displayNamePattern;

    @Convert(converter = JsonConverter.ListConverter.class)
    @Column(name = "default_sort_json", columnDefinition = "JSON")
    private List<Object> defaultSortJson;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> configJson;

    @OneToMany(mappedBy = "entity")
    private List<DpField> fields = new ArrayList<>();

    @OneToMany(mappedBy = "entity")
    private List<DpEntityConstraint> constraints = new ArrayList<>();

    public static DpEntity create(
            DpModule module,
            String code,
            String name,
            String tableStrategy
    ) {
        if (module == null) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "module");
        }
        if (code == null || code.isBlank()) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "entityCode");
        }
        if (name == null || name.isBlank()) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "entityName");
        }
        if (tableStrategy == null || tableStrategy.isBlank()) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "tableStrategy");
        }

        DpEntity e = new DpEntity();
        e.module = module;
        e.entityCode = code;
        e.entityName = name;
        e.tableStrategy = tableStrategy;
        e.isRoot = true;
        e.isActive = true;
        return e;
    }

    public void applyMetadata(
            String entityName,
            String tableStrategy,
            String description,
            Boolean isRoot,
            Boolean isActive,
            String displayNamePattern,
            List<Object> defaultSortJson,
            Map<String, Object> configJson
    ) {
        if (entityName == null || entityName.isBlank()) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "entityName");
        }
        if (tableStrategy == null || tableStrategy.isBlank()) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "tableStrategy");
        }
        if (isRoot == null) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "isRoot");
        }
        if (isActive == null) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "isActive");
        }

        this.entityName = entityName;
        this.tableStrategy = tableStrategy;
        this.description = description;
        this.isRoot = isRoot;
        this.isActive = isActive;
        this.displayNamePattern = displayNamePattern;
        this.defaultSortJson = defaultSortJson;
        this.configJson = configJson;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "DpEntity", getId());
        }
        softDelete(deletedBy);
    }
}
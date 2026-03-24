package com.meta_forge_platform.runtime.domain.model.record;

import com.meta_forge_platform.definition.domain.model.entity.EntityDefinition;
import com.meta_forge_platform.shared.domain.base.BaseVersionedEntity;
import com.meta_forge_platform.shared.domain.constant.CommonStatus;
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
        name = "app_record",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_app_record_entity_code", columnNames = {"entity_id", "record_code"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AppRecord extends BaseVersionedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_entity"))
    private EntityDefinition entity;

    @Column(name = "record_code", length = 100)
    private String recordCode;

    @Column(name = "display_name", length = 500)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private CommonStatus status = CommonStatus.ACTIVE;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "data_json", columnDefinition = "json")
    private Map<String, Object> data = new LinkedHashMap<>();

    public AppRecord(EntityDefinition entity) {
        changeEntity(entity);
    }

    public void changeEntity(EntityDefinition entity) {
        if (entity == null) {
            throw new ValidationException("Entity must not be null");
        }
        this.entity = entity;
    }

    public void changeRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public void changeDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void changeStatus(CommonStatus status) {
        if (status == null) {
            throw new ValidationException("Status must not be null");
        }
        this.status = status;
    }

    public void changeData(Map<String, Object> data) {
        this.data = data == null ? new LinkedHashMap<>() : new LinkedHashMap<>(data);
    }
}

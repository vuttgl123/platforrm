package com.meta_forge_platform.runtime.domain.model.record;

import com.meta_forge_platform.definition.domain.model.entity.EntityDefinition;
import com.meta_forge_platform.definition.domain.model.field.FieldDefinition;
import com.meta_forge_platform.shared.domain.base.BaseEntity;
import com.meta_forge_platform.shared.infrastructure.converter.JsonMapConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "app_record_value",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_app_record_value_unique", columnNames = {"record_id", "field_id", "seq_no"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AppRecordValue extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "record_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_value_record"))
    private AppRecord record;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_value_entity"))
    private EntityDefinition entity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_value_field"))
    private FieldDefinition field;

    @Column(name = "seq_no", nullable = false)
    private Integer seqNo = 0;

    @Column(name = "value_string", length = 1000)
    private String valueString;

    @Column(name = "value_text")
    private String valueText;

    @Column(name = "value_integer")
    private Long valueInteger;

    @Column(name = "value_decimal", precision = 20, scale = 6)
    private BigDecimal valueDecimal;

    @Column(name = "value_boolean")
    private Boolean valueBoolean;

    @Column(name = "value_date")
    private LocalDate valueDate;

    @Column(name = "value_datetime")
    private LocalDateTime valueDateTime;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "value_json", columnDefinition = "json")
    private Map<String, Object> valueJson = new LinkedHashMap<>();
}

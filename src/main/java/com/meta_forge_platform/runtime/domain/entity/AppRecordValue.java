package com.meta_forge_platform.runtime.domain.entity;

import com.meta_forge_platform.platform.domain.entity.DpEntity;
import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app_record_value",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_app_record_value_unique",
                columnNames = {"record_id", "field_id", "seq_no"}))
@SQLDelete(sql = "UPDATE app_record_value SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class AppRecordValue extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "record_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_value_record"))
    private AppRecord record;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_value_entity"))
    private DpEntity entity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_value_field"))
    private DpField field;

    @Column(name = "seq_no", nullable = false)
    @Builder.Default
    private Integer seqNo = 0;

    @Column(name = "value_string", length = 1000)
    private String valueString;

    @Column(name = "value_text", columnDefinition = "LONGTEXT")
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
    private LocalDateTime valueDatetime;

    @Column(name = "value_json", columnDefinition = "JSON")
    private String valueJson;

    public Object resolveValue() {
        if (valueString   != null) return valueString;
        if (valueText     != null) return valueText;
        if (valueInteger  != null) return valueInteger;
        if (valueDecimal  != null) return valueDecimal;
        if (valueBoolean  != null) return valueBoolean;
        if (valueDate     != null) return valueDate;
        if (valueDatetime != null) return valueDatetime;
        if (valueJson     != null) return valueJson;
        return null;
    }

    public void setValue(String dataType, Object value) {
        if (value == null) return;
        switch (dataType.toUpperCase()) {
            case "TEXT", "AUTO_NUMBER", "SELECT" -> valueString = value.toString();
            case "RICH_TEXT", "FORMULA"          -> valueText   = value.toString();
            case "NUMBER", "REFERENCE"           -> valueInteger = ((Number) value).longValue();
            case "DECIMAL"                       -> valueDecimal = new BigDecimal(value.toString());
            case "BOOLEAN"                       -> valueBoolean = (Boolean) value;
            case "DATE"                          -> valueDate = (LocalDate) value;
            case "DATETIME"                      -> valueDatetime = (LocalDateTime) value;
            case "JSON", "MULTI_SELECT"          -> valueJson = value.toString();
            default                              -> valueString = value.toString();
        }
    }
}
package com.meta_forge_platform.runtime.domain.entity;

import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "app_record_value",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_app_record_value_unique",
                columnNames = {"record_id", "field_id", "seq_no"}
        )
)
public class AppRecordValue extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "record_id", nullable = false)
    private AppRecord record;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id", nullable = false)
    private DpField field;

    @Column(name = "seq_no", nullable = false)
    private Integer seqNo;

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

    public static AppRecordValue create(AppRecord record, DpField field, Integer seqNo) {
        AppRecordValue value = new AppRecordValue();
        value.record = record;
        value.field = field;
        value.seqNo = seqNo == null ? 0 : seqNo;
        return value;
    }

    public void writeString(String value) {
        clearValues();
        this.valueString = value;
    }

    public void writeText(String value) {
        clearValues();
        this.valueText = value;
    }

    public void writeInteger(Long value) {
        clearValues();
        this.valueInteger = value;
    }

    public void writeDecimal(BigDecimal value) {
        clearValues();
        this.valueDecimal = value;
    }

    public void writeBoolean(Boolean value) {
        clearValues();
        this.valueBoolean = value;
    }

    public void writeDate(LocalDate value) {
        clearValues();
        this.valueDate = value;
    }

    public void writeDateTime(LocalDateTime value) {
        clearValues();
        this.valueDatetime = value;
    }

    public void writeJson(String value) {
        clearValues();
        this.valueJson = value;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, getId());
        }
        softDelete(deletedBy);
    }

    private void clearValues() {
        this.valueString = null;
        this.valueText = null;
        this.valueInteger = null;
        this.valueDecimal = null;
        this.valueBoolean = null;
        this.valueDate = null;
        this.valueDatetime = null;
        this.valueJson = null;
    }
}
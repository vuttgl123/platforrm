package com.meta_forge_platform.runtime.domain.entity;

import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "app_record_blob",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_app_record_blob_unique",
                columnNames = {"record_id", "field_id", "blob_id"}
        )
)
public class AppRecordBlob extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "record_id", nullable = false)
    private AppRecord record;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id", nullable = false)
    private DpField field;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blob_id", nullable = false)
    private AppBlob blob;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    public static AppRecordBlob create(AppRecord record, DpField field, AppBlob blob) {
        AppRecordBlob binding = new AppRecordBlob();
        binding.record = record;
        binding.field = field;
        binding.blob = blob;
        binding.sortOrder = 0;
        return binding;
    }

    public void applyMetadata(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "AppRecordBlob", getId());
        }
        softDelete(deletedBy);
    }
}
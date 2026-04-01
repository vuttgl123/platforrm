package com.meta_forge_platform.runtime.domain.entity;

import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app_record_blob",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_app_record_blob_unique",
                columnNames = {"record_id", "field_id", "blob_id"}))
@SQLDelete(sql = "UPDATE app_record_blob SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class AppRecordBlob extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "record_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_blob_record"))
    private AppRecord record;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_blob_field"))
    private DpField field;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blob_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_blob_blob"))
    private AppBlob blob;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    private Integer sortOrder = 0;

    public String getFileName() {
        return blob != null ? blob.getFileName() : null;
    }

    public boolean isImage() {
        return blob != null && blob.isImage();
    }
}
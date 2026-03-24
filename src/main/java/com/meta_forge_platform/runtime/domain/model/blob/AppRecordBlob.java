package com.meta_forge_platform.runtime.domain.model.blob;

import com.meta_forge_platform.definition.domain.model.field.FieldDefinition;
import com.meta_forge_platform.runtime.domain.model.record.AppRecord;
import com.meta_forge_platform.shared.domain.base.BaseSortableEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "app_record_blob")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AppRecordBlob extends BaseSortableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "record_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_blob_record"))
    private AppRecord record;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_blob_field"))
    private FieldDefinition field;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blob_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_blob_blob"))
    private AppBlob blob;
}

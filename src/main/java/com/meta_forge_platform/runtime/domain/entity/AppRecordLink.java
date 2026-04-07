package com.meta_forge_platform.runtime.domain.entity;

import com.meta_forge_platform.platform.domain.entity.DpEntityRelation;
import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.runtime.domain.enumeration.RecordLinkType;
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
        name = "app_record_link",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_app_record_link_unique",
                columnNames = {"source_record_id", "field_id", "target_record_id", "link_type"}
        )
)
public class AppRecordLink extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relation_id")
    private DpEntityRelation relation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_record_id", nullable = false)
    private AppRecord sourceRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private DpField field;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_record_id", nullable = false)
    private AppRecord targetRecord;

    @Enumerated(EnumType.STRING)
    @Column(name = "link_type", nullable = false, length = 30)
    private RecordLinkType linkType;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    public static AppRecordLink create(
            AppRecord sourceRecord,
            AppRecord targetRecord,
            DpEntityRelation relation,
            DpField field,
            RecordLinkType linkType
    ) {
        AppRecordLink link = new AppRecordLink();
        link.sourceRecord = sourceRecord;
        link.targetRecord = targetRecord;
        link.relation = relation;
        link.field = field;
        link.linkType = linkType;
        link.sortOrder = 0;
        link.validateTopology();
        return link;
    }

    public void reorder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void changeType(RecordLinkType linkType) {
        this.linkType = linkType;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, getId());
        }
        softDelete(deletedBy);
    }

    private void validateTopology() {
        if (sourceRecord == null || targetRecord == null) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "RECORD_LINK_INVALID");
        }

        if (sourceRecord.getId() != null
                && targetRecord.getId() != null
                && sourceRecord.getId().equals(targetRecord.getId())
                && linkType == RecordLinkType.REFERENCE) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "SELF_REFERENCE_NOT_ALLOWED");
        }

        if (relation != null) {
            Long sourceEntityId = sourceRecord.getEntity().getId();
            Long targetEntityId = targetRecord.getEntity().getId();
            Long relationSourceEntityId = relation.getSourceEntity().getId();
            Long relationTargetEntityId = relation.getTargetEntity().getId();

            if (!relationSourceEntityId.equals(sourceEntityId)
                    || !relationTargetEntityId.equals(targetEntityId)) {
                throw AppException.of(ErrorCode.BAD_REQUEST, "RECORD_LINK_RELATION_MISMATCH");
            }
        }
    }
}
package com.meta_forge_platform.runtime.domain.model.record;

import com.meta_forge_platform.definition.domain.model.entity.EntityRelationDefinition;
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
@Table(name = "app_record_link")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AppRecordLink extends BaseSortableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_record_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_link_source"))
    private AppRecord sourceRecord;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_record_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_link_target"))
    private AppRecord targetRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relation_id", foreignKey = @ForeignKey(name = "fk_app_record_link_relation"))
    private EntityRelationDefinition relation;
}

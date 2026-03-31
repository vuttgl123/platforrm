package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.infrastructure.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dp_view",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_view_entity_code",
                columnNames = {"entity_id", "view_code"}))
@SQLDelete(sql = "UPDATE dp_view SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class DpView extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_view_entity"))
    private DpEntity entity;

    @Column(name = "view_code", nullable = false, length = 100)
    private String viewCode;

    @Column(name = "view_name", nullable = false, length = 255)
    private String viewName;

    @Column(name = "view_type", nullable = false, length = 30)
    @Builder.Default
    private String viewType = "LIST";

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "query_json", nullable = false, columnDefinition = "JSON")
    private Map<String, Object> queryJson;

    @Convert(converter = JsonConverter.ListConverter.class)
    @Column(name = "columns_json", columnDefinition = "JSON")
    private List<Object> columnsJson;

    @Column(name = "is_default", nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
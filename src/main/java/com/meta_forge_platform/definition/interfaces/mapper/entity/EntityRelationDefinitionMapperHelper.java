package com.meta_forge_platform.definition.interfaces.mapper.entity;

import com.meta_forge_platform.shared.domain.enumtype.RelationKind;
import com.meta_forge_platform.shared.domain.enumtype.RelationType;
import org.springframework.stereotype.Component;

@Component
public class EntityRelationDefinitionMapperHelper {

    public RelationType mapRelationType(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return RelationType.valueOf(value.trim().toUpperCase());
    }

    public String mapRelationType(RelationType value) {
        return value == null ? null : value.name();
    }

    public RelationKind mapRelationKind(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return RelationKind.valueOf(value.trim().toUpperCase());
    }

    public String mapRelationKind(RelationKind value) {
        return value == null ? null : value.name();
    }
}

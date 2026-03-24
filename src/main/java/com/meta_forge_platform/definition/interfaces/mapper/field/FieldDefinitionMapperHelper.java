package com.meta_forge_platform.definition.interfaces.mapper.field;

import com.meta_forge_platform.shared.domain.enumtype.RelationType;
import com.meta_forge_platform.shared.domain.enumtype.StorageType;
import org.springframework.stereotype.Component;

@Component
public class FieldDefinitionMapperHelper {

    public StorageType mapStorageType(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return StorageType.valueOf(value.trim().toUpperCase());
    }

    public String mapStorageType(StorageType value) {
        return value == null ? null : value.name();
    }

    public RelationType mapRelationType(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return RelationType.valueOf(value.trim().toUpperCase());
    }

    public String mapRelationType(RelationType value) {
        return value == null ? null : value.name();
    }
}

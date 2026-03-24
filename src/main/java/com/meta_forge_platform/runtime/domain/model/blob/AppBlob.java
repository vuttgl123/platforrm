package com.meta_forge_platform.runtime.domain.model.blob;

import com.meta_forge_platform.shared.domain.base.BaseEntity;
import com.meta_forge_platform.shared.domain.exception.ValidationException;
import com.meta_forge_platform.shared.infrastructure.converter.JsonMapConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "app_blob",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_app_blob_code", columnNames = {"blob_code"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AppBlob extends BaseEntity {

    @Column(name = "blob_code", nullable = false, length = 100)
    private String code;

    @Column(name = "file_name", nullable = false, length = 500)
    private String fileName;

    @Column(name = "mime_type", length = 255)
    private String mimeType;

    @Column(name = "file_size", nullable = false)
    private Long fileSize = 0L;

    @Column(name = "storage_key", nullable = false, length = 1000)
    private String storageKey;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "metadata_json", columnDefinition = "json")
    private Map<String, Object> metadata = new LinkedHashMap<>();

    public AppBlob(String code, String fileName, String storageKey) {
        changeCode(code);
        changeFileName(fileName);
        changeStorageKey(storageKey);
    }

    public void changeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("Blob code must not be blank");
        }
        this.code = code.trim();
    }

    public void changeFileName(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new ValidationException("File name must not be blank");
        }
        this.fileName = fileName.trim();
    }

    public void changeMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void changeFileSize(Long fileSize) {
        if (fileSize == null || fileSize < 0) {
            throw new ValidationException("File size must be >= 0");
        }
        this.fileSize = fileSize;
    }

    public void changeStorageKey(String storageKey) {
        if (storageKey == null || storageKey.isBlank()) {
            throw new ValidationException("Storage key must not be blank");
        }
        this.storageKey = storageKey.trim();
    }

    public void changeMetadata(Map<String, Object> metadata) {
        this.metadata = metadata == null ? new LinkedHashMap<>() : new LinkedHashMap<>(metadata);
    }
}

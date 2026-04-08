package com.meta_forge_platform.runtime.domain.entity;

import com.meta_forge_platform.runtime.domain.enumeration.BlobStorageProvider;
import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import com.meta_forge_platform.shared.infrastructure.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "app_blob",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_app_blob_code",
                columnNames = "blob_code"
        )
)
public class AppBlob extends SoftDeletableEntity {

    @Column(name = "blob_code", length = 100)
    private String blobCode;

    @Column(name = "file_name", nullable = false, length = 500)
    private String fileName;

    @Column(name = "content_type", length = 255)
    private String contentType;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_provider", nullable = false, length = 50)
    private BlobStorageProvider storageProvider;

    @Column(name = "storage_key", nullable = false, length = 1000)
    private String storageKey;

    @Column(name = "checksum", length = 255)
    private String checksum;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "metadata_json", columnDefinition = "JSON")
    private Map<String, Object> metadata;

    @Version
    @Column(name = "version_no", nullable = false)
    private Long versionNo;

    public static AppBlob create(
            String blobCode,
            String fileName,
            String contentType,
            Long fileSize,
            BlobStorageProvider storageProvider,
            String storageKey,
            String checksum,
            Map<String, Object> metadata
    ) {
        AppBlob blob = new AppBlob();
        blob.blobCode = blobCode;
        blob.fileName = fileName;
        blob.contentType = contentType;
        blob.fileSize = fileSize;
        blob.storageProvider = storageProvider;
        blob.storageKey = storageKey;
        blob.checksum = checksum;
        blob.metadata = metadata;
        return blob;
    }

    public void applyMetadata(
            String fileName,
            Map<String, Object> metadata
    ) {
        this.fileName = fileName;
        this.metadata = metadata;
    }

    public void moveStorage(BlobStorageProvider storageProvider, String storageKey) {
        this.storageProvider = storageProvider;
        this.storageKey = storageKey;
    }

    public boolean isImage() {
        return contentType != null && contentType.startsWith("image/");
    }

    public boolean isPdf() {
        return "application/pdf".equalsIgnoreCase(contentType);
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "AppBlob", getId());
        }
        softDelete(deletedBy);
    }
}
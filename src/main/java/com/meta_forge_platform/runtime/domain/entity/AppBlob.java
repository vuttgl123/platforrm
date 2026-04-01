package com.meta_forge_platform.runtime.domain.entity;

import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.infrastructure.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app_blob",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_app_blob_code",
                columnNames = "blob_code"))
@SQLDelete(sql = "UPDATE app_blob SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class AppBlob extends SoftDeletableEntity {

    @Column(name = "blob_code", length = 100)
    private String blobCode;

    @Column(name = "file_name", nullable = false, length = 500)
    private String fileName;

    @Column(name = "content_type", length = 255)
    private String contentType;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "storage_provider", nullable = false, length = 50)
    @Builder.Default
    private String storageProvider = "LOCAL";

    @Column(name = "storage_key", nullable = false, length = 1000)
    private String storageKey;

    @Column(name = "checksum", length = 255)
    private String checksum;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "metadata_json", columnDefinition = "JSON")
    private Map<String, Object> metadataJson;

    public boolean isImage() {
        return contentType != null && contentType.startsWith("image/");
    }

    public String getExtension() {
        if (fileName == null || !fileName.contains(".")) return "";
        return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
    }

    public String getFileSizeFormatted() {
        if (fileSize == null) return "0 B";
        if (fileSize < 1024) return fileSize + " B";
        if (fileSize < 1024 * 1024) return String.format("%.1f KB", fileSize / 1024.0);
        if (fileSize < 1024 * 1024 * 1024) return String.format("%.1f MB", fileSize / (1024.0 * 1024));
        return String.format("%.1f GB", fileSize / (1024.0 * 1024 * 1024));
    }
}
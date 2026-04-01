package com.meta_forge_platform.runtime.infrastructure.repository;

import com.meta_forge_platform.runtime.domain.entity.AppBlob;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppBlobRepository extends BaseRepository<AppBlob, Long> {

    Optional<AppBlob> findByBlobCodeAndIsDeletedFalse(String blobCode);

    boolean existsByBlobCodeAndIsDeletedFalse(String blobCode);

    // ── Tìm theo storage key ─────────────────────────────────────────────────

    Optional<AppBlob> findByStorageKeyAndIsDeletedFalse(String storageKey);

    // ── Tìm theo checksum (phát hiện duplicate upload) ────────────────────────

    Optional<AppBlob> findByChecksumAndIsDeletedFalse(String checksum);

    // ── Tìm theo storage provider ─────────────────────────────────────────────

    List<AppBlob> findAllByStorageProviderAndIsDeletedFalse(String storageProvider);

    // ── Tìm file theo content type ────────────────────────────────────────────

    List<AppBlob> findAllByContentTypeAndIsDeletedFalse(String contentType);

    // ── Tìm ảnh ──────────────────────────────────────────────────────────────

    @Query("SELECT b FROM AppBlob b WHERE b.isDeleted = false " +
            "AND b.contentType LIKE 'image/%'")
    List<AppBlob> findAllImages();

    // ── Tổng dung lượng file theo storage provider ────────────────────────────

    @Query("SELECT COALESCE(SUM(b.fileSize), 0) FROM AppBlob b " +
            "WHERE b.storageProvider = :provider AND b.isDeleted = false")
    Long sumFileSizeByProvider(@Param("provider") String provider);

    // ── Tìm blob không được gắn với record nào (orphan files) ────────────────

    @Query("SELECT b FROM AppBlob b WHERE b.isDeleted = false " +
            "AND NOT EXISTS (" +
            "  SELECT rb FROM AppRecordBlob rb " +
            "  WHERE rb.blob.id = b.id AND rb.isDeleted = false" +
            ")")
    List<AppBlob> findOrphanBlobs();
}
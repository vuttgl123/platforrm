package com.meta_forge_platform.runtime.application.service.impl;

import com.meta_forge_platform.runtime.application.dto.blob.*;
import com.meta_forge_platform.runtime.application.mapper.AppBlobMapper;
import com.meta_forge_platform.runtime.application.service.AppBlobService;
import com.meta_forge_platform.runtime.domain.entity.AppBlob;
import com.meta_forge_platform.runtime.domain.enumeration.BlobStorageProvider;
import com.meta_forge_platform.runtime.infrastructure.repository.AppBlobRepository;
import com.meta_forge_platform.shared.application.BaseServiceImpl;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AppBlobServiceImpl
        extends BaseServiceImpl<AppBlob, AppBlobDto, CreateAppBlobCmd, UpdateAppBlobCmd, Long>
        implements AppBlobService {

    private final AppBlobRepository blobRepository;
    private final AppBlobMapper mapper;

    public AppBlobServiceImpl(AppBlobRepository blobRepository, AppBlobMapper mapper) {
        super(blobRepository);
        this.blobRepository = blobRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getEntityName() {
        return "AppBlob";
    }

    @Override
    protected AppBlobDto toDto(AppBlob entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected AppBlob toEntity(CreateAppBlobCmd command) {
        return AppBlob.create(
                command.getBlobCode(),
                command.getFileName(),
                command.getContentType(),
                command.getFileSize(),
                toStorageProvider(command.getStorageProvider()),
                command.getStorageKey(),
                command.getChecksum(),
                command.getMetadataJson()
        );
    }

    @Override
    protected void updateEntity(AppBlob entity, UpdateAppBlobCmd command) {
        entity.applyMetadata(
                command.getFileName(),
                command.getMetadataJson()
        );
    }

    @Override
    protected void validateCreateCommand(CreateAppBlobCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getFileName(), "fileName");
        validateNotNull(command.getFileSize(), "fileSize");
        validateNotNull(command.getStorageProvider(), "storageProvider");
        validateNotNull(command.getStorageKey(), "storageKey");
    }

    @Override
    protected void validateUpdateCommand(UpdateAppBlobCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getFileName(), "fileName");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateAppBlobCmd command) {
        if (command.getBlobCode() != null
                && !command.getBlobCode().isBlank()
                && blobRepository.existsByBlobCodeAndIsDeletedFalse(command.getBlobCode())) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "AppBlob",
                    command.getBlobCode()
            );
        }
    }

    @Override
    protected void beforeUpdate(AppBlob entity, UpdateAppBlobCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "AppBlob",
                    entity.getId()
            );
        }
    }

    @Override
    protected Specification<AppBlob> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(cb.coalesce(root.get("fileName"), "")), p),
                cb.like(cb.lower(cb.coalesce(root.get("blobCode"), "")), p)
        );
    }

    @Override
    public AppBlobDto getByCode(String blobCode) {
        return blobRepository.findByBlobCodeAndIsDeletedFalse(blobCode)
                .map(mapper::toDto)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "AppBlob",
                        blobCode
                ));
    }

    private BlobStorageProvider toStorageProvider(String raw) {
        try {
            return BlobStorageProvider.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "storageProvider", raw);
        }
    }
}
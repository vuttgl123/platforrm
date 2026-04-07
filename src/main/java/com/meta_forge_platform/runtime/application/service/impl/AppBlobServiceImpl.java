package com.meta_forge_platform.runtime.application.service.impl;

import com.meta_forge_platform.runtime.application.dto.blob.*;
import com.meta_forge_platform.runtime.application.mapper.AppBlobMapper;
import com.meta_forge_platform.runtime.application.service.AppBlobService;
import com.meta_forge_platform.runtime.domain.entity.AppBlob;
import com.meta_forge_platform.runtime.infrastructure.repository.AppBlobRepository;
import com.meta_forge_platform.shared.application.BaseServiceImpl;
import com.meta_forge_platform.shared.domain.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override protected AppBlobDto toDto(AppBlob e) { return mapper.toDto(e); }
    @Override protected AppBlob toEntity(CreateAppBlobCmd c) { return mapper.toEntity(c); }
    @Override protected void updateEntity(AppBlob e, UpdateAppBlobCmd c) { mapper.updateEntity(e, c); }

    @Override
    protected void beforeCreate(CreateAppBlobCmd cmd) {
        if (cmd.getBlobCode() != null &&
                blobRepository.existsByBlobCodeAndIsDeletedFalse(cmd.getBlobCode()))
            throw AppException.conflict("Blob code đã tồn tại: " + cmd.getBlobCode());
    }

    @Override
    protected Specification<AppBlob> buildKeywordSpec(String kw) {
        String p = "%" + kw.toLowerCase() + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("fileName")), p),
                cb.like(cb.lower(root.get("blobCode")), p)
        );
    }

    @Override
    public AppBlobDto getByCode(String blobCode) {
        return blobRepository.findByBlobCodeAndIsDeletedFalse(blobCode)
                .map(mapper::toDto)
                .orElseThrow(() -> AppException.notFound("Blob", blobCode));
    }
}
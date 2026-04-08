package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.fieldgroup.*;
import com.meta_forge_platform.platform.application.mapper.DpFieldGroupMapper;
import com.meta_forge_platform.platform.application.service.DpFieldGroupService;
import com.meta_forge_platform.platform.domain.entity.DpEntity;
import com.meta_forge_platform.platform.domain.entity.DpFieldGroup;
import com.meta_forge_platform.platform.domain.enumeration.GroupLayoutType;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpFieldGroupRepository;
import com.meta_forge_platform.shared.application.BaseServiceImpl;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
public class DpFieldGroupServiceImpl
        extends BaseServiceImpl<DpFieldGroup, DpFieldGroupDto, CreateDpFieldGroupCmd, UpdateDpFieldGroupCmd, Long>
        implements DpFieldGroupService {

    private final DpFieldGroupRepository fieldGroupRepository;
    private final DpEntityRepository entityRepository;
    private final DpFieldGroupMapper mapper;

    public DpFieldGroupServiceImpl(DpFieldGroupRepository fieldGroupRepository,
                                   DpEntityRepository entityRepository,
                                   DpFieldGroupMapper mapper) {
        super(fieldGroupRepository);
        this.fieldGroupRepository = fieldGroupRepository;
        this.entityRepository = entityRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getEntityName() {
        return "DpFieldGroup";
    }

    @Override
    protected DpFieldGroupDto toDto(DpFieldGroup entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected DpFieldGroup toEntity(CreateDpFieldGroupCmd command) {
        DpEntity dpEntity = entityRepository.findActiveById(command.getEntityId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpEntity",
                        command.getEntityId()
                ));

        DpFieldGroup group = DpFieldGroup.create(
                dpEntity,
                command.getGroupCode(),
                command.getGroupName()
        );

        group.applyMetadata(
                command.getGroupName(),
                command.getSortOrder(),
                toLayoutType(command.getLayoutType()),
                command.getConfigJson(),
                command.getIsActive()
        );

        return group;
    }

    @Override
    protected void updateEntity(DpFieldGroup entity, UpdateDpFieldGroupCmd command) {
        entity.applyMetadata(
                command.getGroupName(),
                command.getSortOrder(),
                toLayoutType(command.getLayoutType()),
                command.getConfigJson(),
                command.getIsActive()
        );
    }

    @Override
    protected void validateCreateCommand(CreateDpFieldGroupCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getEntityId(), "entityId");
        validateNotNull(command.getGroupCode(), "groupCode");
        validateNotNull(command.getGroupName(), "groupName");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getLayoutType(), "layoutType");
        validateNotNull(command.getIsActive(), "isActive");
    }

    @Override
    protected void validateUpdateCommand(UpdateDpFieldGroupCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getGroupName(), "groupName");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getLayoutType(), "layoutType");
        validateNotNull(command.getIsActive(), "isActive");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateDpFieldGroupCmd command) {
        if (fieldGroupRepository.existsByEntity_IdAndGroupCodeAndIsDeletedFalse(
                command.getEntityId(),
                command.getGroupCode()
        )) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "DpFieldGroup",
                    command.getGroupCode()
            );
        }
    }

    @Override
    protected void beforeUpdate(DpFieldGroup entity, UpdateDpFieldGroupCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "DpFieldGroup",
                    entity.getId()
            );
        }
    }

    @Override
    protected Specification<DpFieldGroup> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("groupCode")), p),
                cb.like(cb.lower(root.get("groupName")), p)
        );
    }

    @Override
    public List<DpFieldGroupSummaryDto> findAllByEntity(Long entityId) {
        return mapper.toSummaryDtoList(
                fieldGroupRepository.findAllByEntity_IdAndIsDeletedFalseOrderBySortOrderAsc(entityId)
        );
    }

    private GroupLayoutType toLayoutType(String raw) {
        try {
            return GroupLayoutType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "layoutType", raw);
        }
    }
}
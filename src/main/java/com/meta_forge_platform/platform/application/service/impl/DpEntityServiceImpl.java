package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.entity.CreateDpEntityCmd;
import com.meta_forge_platform.platform.application.dto.entity.DpEntityDto;
import com.meta_forge_platform.platform.application.dto.entity.DpEntitySummaryDto;
import com.meta_forge_platform.platform.application.dto.entity.UpdateDpEntityCmd;
import com.meta_forge_platform.platform.application.mapper.DpEntityMapper;
import com.meta_forge_platform.platform.application.service.DpEntityService;
import com.meta_forge_platform.platform.domain.entity.DpEntity;
import com.meta_forge_platform.platform.domain.entity.DpModule;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpModuleRepository;
import com.meta_forge_platform.shared.application.BaseServiceImpl;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class DpEntityServiceImpl
        extends BaseServiceImpl<DpEntity, DpEntityDto, CreateDpEntityCmd, UpdateDpEntityCmd, Long>
        implements DpEntityService {

    private final DpEntityRepository entityRepository;
    private final DpModuleRepository moduleRepository;
    private final DpEntityMapper mapper;

    public DpEntityServiceImpl(DpEntityRepository entityRepository,
                               DpModuleRepository moduleRepository,
                               DpEntityMapper mapper) {
        super(entityRepository);
        this.entityRepository = entityRepository;
        this.moduleRepository = moduleRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getEntityName() {
        return "DpEntity";
    }

    @Override
    protected DpEntityDto toDto(DpEntity entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected DpEntity toEntity(CreateDpEntityCmd command) {
        DpModule module = moduleRepository.findActiveById(command.getModuleId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpModule",
                        command.getModuleId()
                ));

        DpEntity entity = DpEntity.create(
                module,
                command.getEntityCode(),
                command.getEntityName(),
                command.getTableStrategy()
        );

        entity.applyMetadata(
                command.getEntityName(),
                command.getTableStrategy(),
                command.getDescription(),
                command.getIsRoot() != null ? command.getIsRoot() : Boolean.TRUE,
                command.getIsActive() != null ? command.getIsActive() : Boolean.TRUE,
                command.getDisplayNamePattern(),
                command.getDefaultSortJson(),
                command.getConfigJson()
        );

        return entity;
    }

    @Override
    protected void updateEntity(DpEntity entity, UpdateDpEntityCmd command) {
        entity.applyMetadata(
                command.getEntityName(),
                command.getTableStrategy(),
                command.getDescription(),
                command.getIsRoot(),
                command.getIsActive(),
                command.getDisplayNamePattern(),
                command.getDefaultSortJson(),
                command.getConfigJson()
        );
    }

    @Override
    protected void validateCreateCommand(CreateDpEntityCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getModuleId(), "moduleId");
        validateNotNull(command.getEntityCode(), "entityCode");
        validateNotNull(command.getEntityName(), "entityName");
        validateNotNull(command.getTableStrategy(), "tableStrategy");
    }

    @Override
    protected void validateUpdateCommand(UpdateDpEntityCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getEntityName(), "entityName");
        validateNotNull(command.getTableStrategy(), "tableStrategy");
        validateNotNull(command.getIsRoot(), "isRoot");
        validateNotNull(command.getIsActive(), "isActive");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateDpEntityCmd command) {
        if (entityRepository.existsByModule_IdAndEntityCodeAndIsDeletedFalse(
                command.getModuleId(), command.getEntityCode())) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "DpEntity",
                    command.getEntityCode()
            );
        }
    }

    @Override
    protected void beforeUpdate(DpEntity entity, UpdateDpEntityCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "DpEntity",
                    entity.getId()
            );
        }
    }

    @Override
    protected Specification<DpEntity> buildKeywordSpec(String keyword) {
        String pattern = "%" + keyword.toLowerCase() + "%";
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("entityCode")), pattern),
                cb.like(cb.lower(root.get("entityName")), pattern),
                cb.like(cb.lower(cb.coalesce(root.get("description"), "")), pattern)
        );
    }

    @Override
    public DpEntityDto getByCode(Long moduleId, String entityCode) {
        validateNotNull(moduleId, "moduleId");
        validateNotNull(entityCode, "entityCode");

        return entityRepository.findByModule_IdAndEntityCodeAndIsDeletedFalse(moduleId, entityCode)
                .map(mapper::toDto)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpEntity",
                        entityCode
                ));
    }

    @Override
    public List<DpEntitySummaryDto> findAllByModule(Long moduleId) {
        validateNotNull(moduleId, "moduleId");
        return mapper.toSummaryDtoList(
                entityRepository.findAllByModule_IdAndIsDeletedFalseOrderByEntityNameAsc(moduleId)
        );
    }

    @Override
    public List<DpEntitySummaryDto> findRootEntitiesByModule(Long moduleId) {
        validateNotNull(moduleId, "moduleId");
        return mapper.toSummaryDtoList(
                entityRepository.findAllByModule_IdAndIsRootTrueAndIsDeletedFalseOrderByEntityNameAsc(moduleId)
        );
    }

    @Override
    public List<DpEntitySummaryDto> findAllSummary() {
        return mapper.toSummaryDtoList(entityRepository.findAllActiveEntities());
    }
}
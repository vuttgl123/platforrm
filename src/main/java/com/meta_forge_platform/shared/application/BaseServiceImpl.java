package com.meta_forge_platform.shared.application;

import com.meta_forge_platform.shared.domain.base.BaseEntity;
import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import com.meta_forge_platform.shared.domain.query.PageQuery;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import com.meta_forge_platform.shared.infrastructure.FilterSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional(readOnly = true)
public abstract class BaseServiceImpl<E extends SoftDeletableEntity, T, C, U, ID>
        implements BaseService<T, C, U, ID>, RestorableService<T, ID> {

    protected final BaseRepository<E, ID> repository;

    protected BaseServiceImpl(BaseRepository<E, ID> repository) {
        this.repository = repository;
    }

    protected abstract T toDto(E entity);

    protected abstract E toEntity(C command);

    protected abstract void updateEntity(E entity, U command);

    /**
     * Nên override ở service con để trả tên resource ổn định,
     * tránh phụ thuộc tên class khi refactor.
     */
    protected String getEntityName() {
        return getClass().getSimpleName()
                .replace("ServiceImpl", "")
                .replace("Service", "");
    }

    @Override
    public Page<T> findAll(PageQuery query) {
        Specification<E> spec = buildSpec(query);
        Page<E> page = repository.findAll(spec, query.toPageable());
        List<T> dtos = page.getContent().stream()
                .map(this::toDto)
                .toList();

        return new PageImpl<>(dtos, query.toPageable(), page.getTotalElements());
    }

    @Override
    public List<T> findAll() {
        return repository.findAll(buildActiveSpec()).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id)
                .filter(entity -> !isSoftDeleted(entity))
                .map(this::toDto);
    }

    @Override
    public T getById(ID id) {
        E entity = repository.findById(id)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        getEntityName(),
                        id
                ));

        if (isSoftDeleted(entity)) {
            throw AppException.of(
                    ErrorCode.RECORD_ALREADY_DELETED,
                    getEntityName(),
                    id
            );
        }

        return toDto(entity);
    }

    @Override
    public boolean existsById(ID id) {
        return repository.findById(id)
                .filter(entity -> !isSoftDeleted(entity))
                .isPresent();
    }

    @Override
    @Transactional
    public T create(C command) {
        validateCreateCommand(command);

        beforeCreate(command);

        E entity = toEntity(command);

        afterToEntity(entity, command);

        E saved = repository.save(entity);

        afterCreate(saved, command);

        log.debug("[{}] Created id={}", getEntityName(), saved.getId());

        return toDto(saved);
    }

    @Override
    @Transactional
    public T update(ID id, U command) {
        validateUpdateCommand(command);

        E entity = repository.findById(id)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        getEntityName(),
                        id
                ));

        if (isSoftDeleted(entity)) {
            throw AppException.of(
                    ErrorCode.RECORD_ALREADY_DELETED,
                    getEntityName(),
                    id
            );
        }

        beforeUpdate(entity, command);

        updateEntity(entity, command);

        E saved = repository.save(entity);

        afterUpdate(saved, command);

        log.debug("[{}] Updated id={}", getEntityName(), id);

        return toDto(saved);
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        E entity = repository.findById(id)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        getEntityName(),
                        id
                ));

        if (isSoftDeleted(entity)) {
            throw AppException.of(
                    ErrorCode.RECORD_ALREADY_DELETED,
                    getEntityName(),
                    id
            );
        }

        beforeDelete(entity);

        performDelete(entity);

        afterDelete(id);

        log.debug("[{}] Deleted id={}", getEntityName(), id);
    }

    @Override
    @Transactional
    public void deleteByIds(List<ID> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }

        ids.forEach(this::deleteById);
    }

    @Override
    @Transactional
    public T restore(ID id) {
        E entity = repository.findById(id)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        getEntityName(),
                        id
                ));

        if (!(entity instanceof SoftDeletableEntity soft)) {
            throw AppException.withMeta(
                    ErrorCode.BAD_REQUEST,
                    getEntityName() + " does not support restore",
                    getEntityName(),
                    id
            );
        }

        if (!soft.isDeleted()) {
            throw AppException.withMeta(
                    ErrorCode.BAD_REQUEST,
                    getEntityName() + " is not deleted",
                    getEntityName(),
                    id
            );
        }

        beforeRestore(entity);

        soft.restore();

        E saved = repository.save(entity);

        afterRestore(saved);

        log.debug("[{}] Restored id={}", getEntityName(), id);

        return toDto(saved);
    }

    protected void performDelete(E entity) {
        if (entity instanceof SoftDeletableEntity soft) {
            soft.softDelete(getCurrentUsername());
            repository.save(entity);
            return;
        }

        repository.delete(entity);
    }

    protected Specification<E> buildSpec(PageQuery query) {
        Specification<E> spec = buildActiveSpec();

        if (query == null) {
            return spec;
        }

        if (query.getFilters() != null && !query.getFilters().isEmpty()) {
            spec = spec.and(FilterSpecification.from(query.getFilters()));
        }

        if (query.getKeyword() != null && !query.getKeyword().isBlank()) {
            Specification<E> keywordSpec = buildKeywordSpec(query.getKeyword().trim());
            if (keywordSpec != null) {
                spec = spec.and(keywordSpec);
            }
        }

        return spec;
    }

    /**
     * Mặc định chỉ lấy record chưa bị xóa mềm nếu entity có field isDeleted.
     */
    protected Specification<E> buildActiveSpec() {
        return (root, query, cb) -> {
            try {
                root.get("isDeleted");
                return cb.isFalse(root.get("isDeleted"));
            } catch (IllegalArgumentException ex) {
                return cb.conjunction();
            }
        };
    }

    /**
     * Override ở service con nếu muốn search keyword riêng.
     */
    protected Specification<E> buildKeywordSpec(String keyword) {
        return null;
    }

    protected boolean isSoftDeleted(E entity) {
        return entity instanceof SoftDeletableEntity soft && soft.isDeleted();
    }

    protected String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return "system";
        }
        return auth.getName();
    }

    protected void validate(boolean condition, ErrorCode errorCode, Object... args) {
        if (!condition) {
            throw AppException.of(errorCode, args);
        }
    }

    protected void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, fieldName);
        }
    }

    protected void validateCreateCommand(C command) {
        validateNotNull(command, "command");
    }

    protected void validateUpdateCommand(U command) {
        validateNotNull(command, "command");
    }

    protected void beforeCreate(C command) {
    }

    protected void afterToEntity(E entity, C command) {
    }

    protected void afterCreate(E entity, C command) {
    }

    protected void beforeUpdate(E entity, U command) {
    }

    protected void afterUpdate(E entity, U command) {
    }

    protected void beforeDelete(E entity) {
    }

    protected void afterDelete(ID id) {
    }

    protected void beforeRestore(E entity) {
    }

    protected void afterRestore(E entity) {
    }
}
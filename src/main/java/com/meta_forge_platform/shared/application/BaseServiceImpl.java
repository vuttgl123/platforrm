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
public abstract class BaseServiceImpl<E extends BaseEntity, T, C, U, ID> implements BaseService<T, C, U, ID> {

    protected final BaseRepository<E, ID> repository;

    protected BaseServiceImpl(BaseRepository<E, ID> repository) {
        this.repository = repository;
    }

    protected abstract T toDto(E entity);

    protected abstract E toEntity(C command);

    protected abstract void updateEntity(E entity, U command);

    @Override
    public Page<T> findAll(PageQuery query) {
        Specification<E> spec = buildSpec(query);
        Page<E> page = repository.findAll(spec, query.toPageable());
        List<T> dtos = page.getContent().stream().map(this::toDto).toList();
        return new PageImpl<>(dtos, query.toPageable(), page.getTotalElements());
    }

    @Override
    public List<T> findAll() {
        Specification<E> spec = buildActiveSpec();
        return repository.findAll(spec).stream().map(this::toDto).toList();
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id)
                .filter(e -> !isSoftDeleted(e))
                .map(this::toDto);
    }

    @Override
    public T getById(ID id) {
        E entity = repository.findById(id).orElseThrow(() -> AppException.notFound(getEntityName(), id));
        if (isSoftDeleted(entity)) {
            throw AppException.alreadyDeleted(getEntityName(), id);
        }
        return toDto(entity);
    }

    @Override
    public boolean existsById(ID id) {
        return repository.findById(id)
                .filter(e -> !isSoftDeleted(e))
                .isPresent();
    }

    @Override
    @Transactional
    public T create(C command) {
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
        E entity = repository.findById(id).orElseThrow(() -> AppException.notFound(getEntityName(), id));
        if (isSoftDeleted(entity)) {
            throw AppException.alreadyDeleted(getEntityName(), id);
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
        E entity = repository.findById(id).orElseThrow(() -> AppException.notFound(getEntityName(), id));
        if (isSoftDeleted(entity)) {
            throw AppException.alreadyDeleted(getEntityName(), id);
        }
        beforeDelete(entity);
        performDelete(entity);
        afterDelete(id);
        log.debug("[{}] Deleted id={}", getEntityName(), id);
    }

    @Override
    @Transactional
    public void deleteByIds(List<ID> ids) {
        ids.forEach(this::deleteById);
    }


    protected void performDelete(E entity) {
        if (entity instanceof SoftDeletableEntity soft) {
            soft.softDelete(getCurrentUsername());
            repository.save(entity);
        } else {
            repository.delete(entity);
        }
    }

    @Transactional
    public T restore(ID id) {
        E entity = repository.findById(id).orElseThrow(() -> AppException.notFound(getEntityName(), id));
        if (!(entity instanceof SoftDeletableEntity soft)) {
            throw AppException.badRequest(getEntityName() + " không hỗ trợ khôi phục");
        }
        if (!soft.isDeleted()) {
            throw AppException.badRequest(getEntityName() + " chưa bị xóa: " + id);
        }
        soft.restore();
        return toDto(repository.save(entity));
    }

    protected Specification<E> buildSpec(PageQuery query) {
        Specification<E> spec = buildActiveSpec();

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

    protected Specification<E> buildActiveSpec() {
        return (root, query, cb) -> {
            try {
                root.get("isDeleted");
                return cb.equal(root.get("isDeleted"), false);
            } catch (IllegalArgumentException e) {
                return cb.conjunction();
            }
        };
    }

    protected Specification<E> buildKeywordSpec(String keyword) {
        return null;
    }

    protected void beforeCreate(C command) {}

    protected void afterToEntity(E entity, C command) {}

    protected void afterCreate(E entity, C command) {}

    protected void beforeUpdate(E entity, U command) {}

    protected void afterUpdate(E entity, U command) {}

    protected void beforeDelete(E entity) {}

    protected void afterDelete(ID id) {}

    protected String getEntityName() {
        return getClass().getSimpleName().replace("ServiceImpl", "").replace("Service", "");
    }

    protected boolean isSoftDeleted(E entity) {
        if (entity instanceof SoftDeletableEntity soft) {
            return soft.isDeleted();
        }
        return false;
    }

    protected String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return "system";
        }
        return auth.getName();
    }

    protected void validate(boolean condition, ErrorCode errorCode, String message) {
        if (!condition) throw new AppException(errorCode, message);
    }

    protected void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new AppException(ErrorCode.FIELD_REQUIRED, "Field '" + fieldName + "' không được để trống");
        }
    }
}
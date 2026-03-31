package com.meta_forge_platform.shared.infrastructure;

import com.meta_forge_platform.shared.domain.query.FilterQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class FilterSpecification<T> implements Specification<T> {

    private final List<FilterQuery> filters;

    public static <T> Specification<T> from(List<FilterQuery> filters) {
        return new FilterSpecification<>(filters);
    }

    public static <T> Specification<T> fromWithActive(List<FilterQuery> filters) {
        Specification<T> activeSpec = (root, query, cb) ->
                cb.equal(root.get("isDeleted"), false);
        return activeSpec.and(new FilterSpecification<>(filters));
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (filters == null || filters.isEmpty()) {
            return cb.conjunction();
        }

        Predicate[] predicates = filters.stream()
                .map(f -> buildPredicate(f, root, cb))
                .toArray(Predicate[]::new);

        return cb.and(predicates);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Predicate buildPredicate(FilterQuery f, Root<T> root, CriteriaBuilder cb) {
        Path<?> path = resolvePath(root, f.getField());
        Object value = f.getValue();

        return switch (f.getOperator()) {
            case EQ       -> cb.equal(path, coerce(path, value));
            case NEQ      -> cb.notEqual(path, coerce(path, value));
            case LIKE     -> cb.like(cb.lower((Path<String>) path),
                    "%" + value.toString().toLowerCase() + "%");
            case STARTS   -> cb.like(cb.lower((Path<String>) path),
                    value.toString().toLowerCase() + "%");
            case GT       -> cb.greaterThan((Path<Comparable>) path, (Comparable) coerce(path, value));
            case GTE      -> cb.greaterThanOrEqualTo((Path<Comparable>) path, (Comparable) coerce(path, value));
            case LT       -> cb.lessThan((Path<Comparable>) path, (Comparable) coerce(path, value));
            case LTE      -> cb.lessThanOrEqualTo((Path<Comparable>) path, (Comparable) coerce(path, value));
            case IN       -> path.in(toCollection(value));
            case NOT_IN   -> cb.not(path.in(toCollection(value)));
            case BETWEEN  -> cb.between((Path<Comparable>) path,
                    (Comparable) coerce(path, value),
                    (Comparable) coerce(path, f.getValue2()));
            case IS_NULL  -> cb.isNull(path);
            case NOT_NULL -> cb.isNotNull(path);
        };
    }

    private Path<?> resolvePath(Root<T> root, String field) {
        String[] parts = field.split("\\.");
        Path<?> path = root.get(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            path = ((Path<?>) path).get(parts[i]);
        }
        return path;
    }

    private Object coerce(Path<?> path, Object value) {
        if (value == null) return null;
        Class<?> javaType = path.getJavaType();
        if (javaType == null || javaType.isInstance(value)) return value;

        String str = value.toString();
        if (javaType == Long.class || javaType == long.class) return Long.parseLong(str);
        if (javaType == Integer.class || javaType == int.class) return Integer.parseInt(str);
        if (javaType == Double.class || javaType == double.class) return Double.parseDouble(str);
        if (javaType == Boolean.class || javaType == boolean.class) return Boolean.parseBoolean(str);
        if (javaType == LocalDate.class) return LocalDate.parse(str);
        if (javaType == LocalDateTime.class) return LocalDateTime.parse(str);
        if (javaType.isEnum()) return Enum.valueOf((Class<Enum>) javaType, str);
        return value;
    }

    private Collection<?> toCollection(Object value) {
        if (value instanceof Collection<?> c) return c;
        return List.of(value);
    }
}
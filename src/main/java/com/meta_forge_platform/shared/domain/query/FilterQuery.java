package com.meta_forge_platform.shared.domain.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Mô tả một điều kiện filter động.
 *
 * Operator:
 *  EQ      → field = value
 *  NEQ     → field != value
 *  LIKE    → field LIKE %value%
 *  STARTS  → field LIKE value%
 *  IN      → field IN (values)
 *  NOT_IN  → field NOT IN (values)
 *  GT      → field > value
 *  GTE     → field >= value
 *  LT      → field < value
 *  LTE     → field <= value
 *  BETWEEN → field BETWEEN value AND value2
 *  IS_NULL → field IS NULL
 *  NOT_NULL→ field IS NOT NULL
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterQuery {

    private String field;
    private Operator operator;
    private Object value;
    private Object value2;

    public enum Operator {
        EQ, NEQ,
        LIKE, STARTS,
        IN, NOT_IN,
        GT, GTE, LT, LTE,
        BETWEEN,
        IS_NULL, NOT_NULL
    }

    public static FilterQuery of(String field, Operator operator, Object value) {
        return new FilterQuery(field, operator, value, null);
    }

    public static FilterQuery between(String field, Object from, Object to) {
        return new FilterQuery(field, Operator.BETWEEN, from, to);
    }

    public static FilterQuery in(String field, List<?> values) {
        return new FilterQuery(field, Operator.IN, values, null);
    }

    public static FilterQuery eq(String field, Object value) {
        return of(field, Operator.EQ, value);
    }

    public static FilterQuery like(String field, String keyword) {
        return of(field, Operator.LIKE, keyword);
    }

    public static FilterQuery isNull(String field) {
        return of(field, Operator.IS_NULL, null);
    }

    public static FilterQuery notNull(String field) {
        return of(field, Operator.NOT_NULL, null);
    }

    public static FilterQuery parse(String raw) {
        String[] parts = raw.split(":", 3);
        if (parts.length < 2) throw new IllegalArgumentException("Invalid filter: " + raw);
        String field = parts[0].trim();
        Operator op = Operator.valueOf(parts[1].trim().toUpperCase());
        Object value = parts.length > 2 ? parts[2].trim() : null;
        return of(field, op, value);
    }
}
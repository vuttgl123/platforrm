package com.meta_forge_platform.shared.domain.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortQuery {

    private String field;
    private Direction direction = Direction.ASC;

    public enum Direction {
        ASC, DESC;

        public Sort.Direction toSpring() {
            return this == ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
        }
    }

    public static SortQuery of(String field, Direction direction) {
        return new SortQuery(field, direction);
    }

    public static SortQuery parse(String raw) {
        String[] parts = raw.split(",");
        String field = parts[0].trim();
        Direction dir = parts.length > 1 && "desc".equalsIgnoreCase(parts[1].trim())
                ? Direction.DESC : Direction.ASC;
        return new SortQuery(field, dir);
    }

    public Sort.Order toOrder() {
        return new Sort.Order(direction.toSpring(), field);
    }
}
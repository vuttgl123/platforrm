package com.meta_forge_platform.shared.domain.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Query object cho tất cả request list/search.
 * Controller nhận vào, truyền xuống Service → Repository.
 *
 * Ví dụ request:
 * GET /api/records?page=0&size=20&sort=createdAt,desc&filter=status:ACTIVE&filter=name:~foo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageQuery {

    @Builder.Default
    private int page = 0;

    @Builder.Default
    private int size = 20;

    @Builder.Default
    private List<SortQuery> sorts = new ArrayList<>();

    @Builder.Default
    private List<FilterQuery> filters = new ArrayList<>();

    private String keyword;

    public int getOffset() {
        return page * size;
    }

    public PageQuery withFilter(String field, FilterQuery.Operator operator, Object value) {
        this.filters.add(FilterQuery.of(field, operator, value));
        return this;
    }

    public PageQuery withSort(String field, SortQuery.Direction direction) {
        this.sorts.add(SortQuery.of(field, direction));
        return this;
    }

    public PageRequest toPageable() {
        if (sorts.isEmpty()) {
            return PageRequest.of(page, size);
        }
        List<Sort.Order> orders = sorts.stream()
                .map(SortQuery::toOrder)
                .toList();
        return PageRequest.of(
                page, size, Sort.by(orders));
    }
}
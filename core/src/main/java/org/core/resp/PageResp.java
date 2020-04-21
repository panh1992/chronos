package org.core.resp;

import lombok.Getter;

import java.util.List;

/**
 * 分页实体
 */
@Getter
public class PageResp<T> {

    private final long limit;

    private final long offset;

    private final long totalElements;

    private final List<T> content;

    /**
     * 创建分页响应
     */
    private PageResp(List<T> content, long limit, long offset, long total) {
        this.content = content;
        this.limit = limit;
        this.offset = offset;
        this.totalElements = total;
    }

    public static <T> PageResp<T> of(List<T> content, long limit, long offset, long total) {
        return new PageResp<>(content, limit, offset, total);
    }

    public static <T> PageResp<T> of(List<T> content, long limit, long offset) {
        return new PageResp<>(content, limit, offset, 0L);
    }

}

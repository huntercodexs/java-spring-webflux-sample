package com.webflux.sample.config.mongo;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class CurrentPage<T> extends PageImpl<T> {

    private long limit;
    private long offset;
    private long total;

    public CurrentPage(List<T> content, long limit, long offset, long total) {
        super(content, PageRequest.of((int) (offset / limit), (int) limit), total);
        this.limit = limit;
        this.offset = offset;
        this.total = total;
    }

    public boolean hasNext() {
        return this.limit + this.offset < this.total;
    }

    public long getTotalElements() {
        return this.total;
    }
}

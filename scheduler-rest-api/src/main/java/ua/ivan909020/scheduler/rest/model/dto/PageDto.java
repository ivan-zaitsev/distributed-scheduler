package ua.ivan909020.scheduler.rest.model.dto;

import java.util.List;

public class PageDto<T> {

    private final List<T> content;

    private final Integer total;

    public PageDto(List<T> content, Integer total) {
        this.content = content;
        this.total = total;
    }

    public List<T> getContent() {
        return content;
    }

    public Integer getTotal() {
        return total;
    }

}

package ua.ivan909020.scheduler.core.model.domain.page;

import java.util.List;

public class PagedList<T> {

    private final List<T> content;

    private final String nextCursor;

    public PagedList(List<T> content, String nextCursor) {
        this.content = content;
        this.nextCursor = nextCursor;
    }

    public List<T> getContent() {
        return content;
    }

    public String getNextCursor() {
        return nextCursor;
    }

}

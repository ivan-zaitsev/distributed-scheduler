package ua.ivan909020.scheduler.core.model.domain.page;

public class KeysetPage {

    private final Integer size;
    private final String cursor;

    public KeysetPage(int size, String cursor) {
        this.size = size;
        this.cursor = cursor;
    }

    public int getSize() {
        return size;
    }

    public String getCursor() {
        return cursor;
    }

}

package ua.ivan909020.scheduler.core.model.domain.page;

import java.util.List;

public record PagedList<T>(List<T> content, String nextCursor) {

}

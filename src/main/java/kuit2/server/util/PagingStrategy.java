package kuit2.server.util;

public interface PagingStrategy {
    boolean hasNext(long lastId, long categoryId);

}

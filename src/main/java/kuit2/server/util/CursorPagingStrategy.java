package kuit2.server.util;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class CursorPagingStrategy implements PagingStrategy {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CursorPagingStrategy(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean hasNext(long lastId, long categoryId) {

        //커서 페이징 hasNext 구현

        return false;
    }
}

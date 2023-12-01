package kuit2.server.util;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class OffsetPagingStrategy implements PagingStrategy {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OffsetPagingStrategy(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean hasNext(long lastId, long categoryId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM restaurant join restaurant_category rc on restaurant.restaurant_id = rc.restaurant_id WHERE restaurant.restaurant_id > :lastId and rc.category_id = :categoryId LIMIT 1);";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("lastId", lastId);
        parameters.addValue("categoryId", categoryId);

        return jdbcTemplate.queryForObject(sql, parameters, Boolean.class);
    }
}

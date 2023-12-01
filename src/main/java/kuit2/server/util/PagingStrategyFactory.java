package kuit2.server.util;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class PagingStrategyFactory {
    public static PagingStrategy getPagingStrategy(String sortBy, NamedParameterJdbcTemplate jdbcTemplate) {
        if ("lowDeliveryTip".equals(sortBy)) {
            return new CursorPagingStrategy(jdbcTemplate);
        } else{
            return new OffsetPagingStrategy(jdbcTemplate);
        }
    }
}

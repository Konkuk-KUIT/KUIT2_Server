package kuit2.server.util;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.naming.Name;

public class SortStrategyFactory {

    public static SortStrategy getSortStrategy(String sortBy, NamedParameterJdbcTemplate jdbcTemplate) {
        if ("lowDeliveryTip".equals(sortBy)) {
            return new LowDeliveryTipSortStrategy(jdbcTemplate);
        } else {
            return new DefaultSortStrategy(jdbcTemplate);
        }
    }
}

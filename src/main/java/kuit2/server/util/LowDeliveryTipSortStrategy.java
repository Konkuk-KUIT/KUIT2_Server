package kuit2.server.util;

import kuit2.server.dto.user.GetBriefRestaurantResponse;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class LowDeliveryTipSortStrategy implements SortStrategy {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public LowDeliveryTipSortStrategy(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GetBriefRestaurantResponse> sort(long lastId, long categoryId, String minOrderPrice) {

        //배달팁 낮은 순으로 레스토랑 정렬

        return null;
    }
}

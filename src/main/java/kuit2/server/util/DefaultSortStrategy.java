package kuit2.server.util;

import kuit2.server.dto.user.GetBriefRestaurantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

public class DefaultSortStrategy implements SortStrategy {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DefaultSortStrategy(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GetBriefRestaurantResponse> sort(long lastId, long categoryId, String minOrderPrice) {

        String sql = "select r.restaurant_id, r.name, r.min_order_price from restaurant As r join restaurant_category rc on r.restaurant_id = rc.restaurant_id where rc.category_id=:categoryId and r.min_order_price > :minOrderPrice limit 2 offset :lastId;";
        Map<String, Object> param = Map.of("categoryId", categoryId, "minOrderPrice", minOrderPrice, "lastId", lastId);


        return jdbcTemplate.query(
                sql,
                param,
                (resultSet, rowNum) -> {
                    GetBriefRestaurantResponse getBriefRestaurantResponse = new GetBriefRestaurantResponse();
                    getBriefRestaurantResponse.setRestaurantId((resultSet.getLong("restaurant_id")));
                    getBriefRestaurantResponse.setRestaurantName(resultSet.getString("name"));
                    getBriefRestaurantResponse.setStar_count(0);
                    getBriefRestaurantResponse.setReview_count(0);
                    getBriefRestaurantResponse.setRepresentMenu(null);
                    getBriefRestaurantResponse.setEta(null);
                    getBriefRestaurantResponse.setMinOrderPrice(resultSet.getFloat("min_order_price"));

                    return getBriefRestaurantResponse;
                });
    }
}

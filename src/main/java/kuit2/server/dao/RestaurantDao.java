package kuit2.server.dao;

import kuit2.server.dto.user.GetBriefRestaurantResponse;
import kuit2.server.dto.user.GetUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
@Repository
public class RestaurantDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RestaurantDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    public GetBriefRestaurantResponse getBriefRestaurantById(long restaurantId) {
        String sql = "select name, min_order_price from restaurant where restaurant_id=:restaurant_id";
        Map<String, Object> param = Map.of("restaurant_id", restaurantId);
        return jdbcTemplate.queryForObject(
                sql,
                param,
                (resultSet, rowNum) -> {
                    GetBriefRestaurantResponse getBriefRestaurantResponse = new GetBriefRestaurantResponse();
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

package kuit2.server.dao;

import kuit2.server.dto.restaurant.GetCategoriesResponse;
import kuit2.server.dto.user.GetBriefRestaurantResponse;
import kuit2.server.dto.user.GetUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
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

    public GetCategoriesResponse getCategories() {
        String sql = "SELECT name FROM category_restaurant";
        GetCategoriesResponse getCategoriesResponse = new GetCategoriesResponse();
        getCategoriesResponse.setCategories(
                jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            String categoryName;
            categoryName = resultSet.getString("name");
            return categoryName;
        }));
        return getCategoriesResponse;
    }

    public List<GetBriefRestaurantResponse> getRestaurants(long categoryId, String sortBy, String minOrderPrice) {
        String sql = "select r.name, r.min_order_price from restaurant As r join restaurant_category rc on r.restaurant_id = rc.restaurant_id where rc.category_id=:categoryId and r.min_order_price > :minOrderPrice;";
        Map<String, Object> param = Map.of("categoryId", categoryId, "minOrderPrice", minOrderPrice);
        return jdbcTemplate.query(
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

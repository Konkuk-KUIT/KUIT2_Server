package kuit2.server.dao;

import kuit2.server.dto.restaurant.GetBriefMenuResponse;
import kuit2.server.dto.restaurant.GetCategoriesResponse;
import kuit2.server.dto.restaurant.GetRestaurantMenuResponse;
import kuit2.server.dto.user.GetBriefRestaurantResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
        String sql = "select restaurant_id, name, min_order_price from restaurant where restaurant_id=:restaurant_id";
        Map<String, Object> param = Map.of("restaurant_id", restaurantId);
        return jdbcTemplate.queryForObject(
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
        String sql = "select r.restaurant_id, r.name, r.min_order_price from restaurant As r join restaurant_category rc on r.restaurant_id = rc.restaurant_id where rc.category_id=:categoryId and r.min_order_price > :minOrderPrice;";
        Map<String, Object> param = Map.of("categoryId", categoryId, "minOrderPrice", minOrderPrice);
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

    public List<GetRestaurantMenuResponse> getRestaurantMenus(long restaurantId) {
        String sql = "SELECT m.menu_id, m.name AS menu_name, m.description AS menu_description, m.price, cm.name AS category " +
                "FROM menu AS m " +
                "JOIN menu_category mc on m.menu_id = mc.menu_id " +
                "JOIN category_menu cm on cm.category_menu_id = mc.category_menu_id " +
                "WHERE m.restaurant_id = :restaurantId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("restaurantId", restaurantId);

        return jdbcTemplate.query(sql, parameters, rs -> {
            Map<String, GetRestaurantMenuResponse> responseMap = new LinkedHashMap<>();
            while (rs.next()) {
                String category = rs.getString("category");
                GetRestaurantMenuResponse response = responseMap.computeIfAbsent(category, k -> {
                    GetRestaurantMenuResponse newResponse = new GetRestaurantMenuResponse();
                    newResponse.setMenuCategory(category);
                    newResponse.setMenus(new ArrayList<>());
                    return newResponse;
                });

                GetBriefMenuResponse briefMenu = new GetBriefMenuResponse();
                briefMenu.setMenuId(rs.getLong("menu_id"));
                briefMenu.setName(rs.getString("menu_name"));
                briefMenu.setDescription(rs.getString("menu_description"));
                briefMenu.setPrice(rs.getFloat("price"));
                briefMenu.setCategory(category);


                response.getMenus().add(briefMenu);
            }
            return new ArrayList<>(responseMap.values());
        });
    }
}

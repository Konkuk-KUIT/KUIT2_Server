package kuit2.server.dao;

import kuit2.server.common.exception.RestaurantException;
import kuit2.server.dto.restaurant.*;
import kuit2.server.dto.user.GetBriefRestaurantResponse;
import kuit2.server.dto.user.MenuOption;
import kuit2.server.dto.user.MenuOptionInCategory;
import kuit2.server.util.PagingStrategy;
import kuit2.server.util.PagingStrategyFactory;
import kuit2.server.util.SortStrategy;
import kuit2.server.util.SortStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.RESTAURANT_NOT_FOUND;

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
        try {
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
        } catch (EmptyResultDataAccessException e) {
            throw new RestaurantException(RESTAURANT_NOT_FOUND);
        }
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

    public GetRestaurantResponse getRestaurants(long lastId, long categoryId, String sortBy, String minOrderPrice) {

        GetRestaurantResponse getRestaurantResponse = new GetRestaurantResponse();

        String sql = "select r.restaurant_id, r.name, r.min_order_price from restaurant As r join restaurant_category rc on r.restaurant_id = rc.restaurant_id where rc.category_id=:categoryId and r.min_order_price > :minOrderPrice limit 2 offset :lastId;";
        Map<String, Object> param = Map.of("categoryId", categoryId, "minOrderPrice", minOrderPrice, "lastId", lastId);

        List<GetBriefRestaurantResponse> restaurants = jdbcTemplate.query(
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

        long lastRestaurantId = restaurants.isEmpty() ? lastId : restaurants.get(restaurants.size() - 1).getRestaurantId();

        boolean hasNext = hasNext(lastRestaurantId, categoryId);


        getRestaurantResponse.setHasNext(hasNext);
        getRestaurantResponse.setRestaurants(restaurants);


        return getRestaurantResponse;
    }

    public boolean hasNext(long lastId, long categoryId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM restaurant join restaurant_category rc on restaurant.restaurant_id = rc.restaurant_id WHERE restaurant.restaurant_id > :lastId and rc.category_id = :categoryId LIMIT 1);";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("lastId", lastId);
        parameters.addValue("categoryId", categoryId);

        return jdbcTemplate.queryForObject(sql, parameters, Boolean.class);
    }

    /**
     * 팩토리 메서드 패턴을 이용해 정렬 기준에따라 다른 로직을 수행하는 Controller
     */
    public GetRestaurantResponse getRestaurantsV2(long lastId, long categoryId, String sortBy, String minOrderPrice) {
        GetRestaurantResponse getRestaurantResponse = new GetRestaurantResponse();

        SortStrategy sortStrategy = SortStrategyFactory.getSortStrategy(sortBy, jdbcTemplate);
        List<GetBriefRestaurantResponse> restaurants = sortStrategy.sort(lastId, categoryId, minOrderPrice);


        long lastRestaurantId = restaurants.isEmpty() ? lastId : restaurants.get(restaurants.size() - 1).getRestaurantId();


        PagingStrategy pagingStrategy = PagingStrategyFactory.getPagingStrategy(sortBy, jdbcTemplate);
        boolean hasNext = pagingStrategy.hasNext(lastRestaurantId, categoryId);


        getRestaurantResponse.setHasNext(hasNext);
        getRestaurantResponse.setRestaurants(restaurants);


        return getRestaurantResponse;
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

    public GetMenuResponse getRestaurantMenu(long restaurantId, long menuId) {

        String sql = "select m.menu_id, m.name, m.description, m.price \n" +
                "from menu AS m \n" +
                "where menu_id=:menuId;";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("menuId", menuId);

        GetMenuResponse getMenuResponse = new GetMenuResponse();

        jdbcTemplate.query(sql, parameters, rs -> {
            getMenuResponse.setMenuId(rs.getLong("menu_id"));
            getMenuResponse.setMenuName(rs.getString("name"));
            getMenuResponse.setMenuDescription(rs.getString("description"));
            getMenuResponse.setMenuPrice(rs.getFloat("price"));
        });

        String sqlForOption = "select moc.name AS optionCategory, mo.name, mo.price " +
                "from menu_option_category AS moc join menu_option AS mo on mo.menu_option_id = moc.menu_option_id " +
                "where mo.menu_id = :menuId;";

        MapSqlParameterSource parametersForOption = new MapSqlParameterSource();
        parametersForOption.addValue("menuId", menuId);

        Map<String, MenuOptionInCategory> optionCategoryMap = new LinkedHashMap<>();

        getMenuResponse.setOptions(jdbcTemplate.query(sqlForOption, parametersForOption, resultSet -> {

            while (resultSet.next()) {
                String optionCategory = resultSet.getString("optionCategory");
                MenuOptionInCategory menuOptionInCategory = optionCategoryMap.computeIfAbsent(
                        optionCategory, k -> new MenuOptionInCategory(k, new ArrayList<>())
                );

                MenuOption menuOption = new MenuOption();
                menuOption.setMenuOptionName(resultSet.getString("mo.name"));
                menuOption.setMenuOptionPrice(resultSet.getFloat("mo.price"));

                menuOptionInCategory.getMenuOptions().add(menuOption);
            }

            return new ArrayList<>(optionCategoryMap.values());
        }));

        return getMenuResponse;
    }
}

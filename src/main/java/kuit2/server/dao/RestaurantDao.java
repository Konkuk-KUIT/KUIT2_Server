package kuit2.server.dao;

import kuit2.server.dto.user.GetCategoryResponse;
import kuit2.server.dto.user.GetStoreDetailResponse;
import kuit2.server.dto.user.GetStoreResponse;
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


    public List<GetCategoryResponse> getCategories() {
        String sql = "select distinct category from Stores where status = 'active'";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new GetCategoryResponse(rs.getString("category"))
        );
    }

    public List<GetStoreResponse> getStoresByCategoryV1(String category, int page, int size) {
        String sql = "SELECT * FROM Stores WHERE category = :category " +
                "ORDER BY storeId LIMIT :limit";

        int limit = size;
        int offset = page * size + 1;

        log.info(String.valueOf(size));
        log.info(String.valueOf(offset));

        Map<String, Object> params = Map.of(
                "category", category,
                "limit", limit,
                "offset", offset
        );

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> new GetStoreResponse(
                rs.getLong("storeId"),
                rs.getString("storeName"),
                rs.getString("storePictureUrl")
        ));
    }

    public List<GetStoreResponse> getStoresByCategoryV2(String category, Long lastStoreId, int size) {
        String sql = "SELECT * FROM Stores WHERE category = :category AND storeId > :lastStoreId " +
                "ORDER BY storeId LIMIT :limit";

        Map<String, Object> params = Map.of(
                "category", category,
                "lastStoreId", lastStoreId,
                "limit", size
        );

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> new GetStoreResponse(
                rs.getLong("storeId"),
                rs.getString("storeName"),
                rs.getString("storeImage")
        ));
    }

    public List<GetStoreDetailResponse> getStore(String storeId) {
        String sql = "select * from Stores where storeId = :storeId";

        Map<String, Object> params = Map.of("storeId", storeId);

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> new GetStoreDetailResponse(
                rs.getString("storeName"),
                rs.getString("storeImage"),
                rs.getString("address"),
                rs.getString("storeHours"),
                rs.getString("closedDays"),
                rs.getInt("numOfLike"),
                rs.getInt("type"),
                rs.getBigDecimal("rating"),
                rs.getString("phone")
        ));
    }
}

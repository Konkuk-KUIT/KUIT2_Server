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

    public List<GetStoreResponse> getStoresByCategory(String category) {
        String sql = "select * from Stores where category = :category and status = 'active'";

        Map<String, Object> params = Map.of("category", category);

        // 쿼리 실행 및 결과 매핑
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

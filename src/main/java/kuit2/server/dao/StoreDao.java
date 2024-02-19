package kuit2.server.dao;

import kuit2.server.dto.store.GetCategoryResponse;
import kuit2.server.dto.store.GetStoreResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class StoreDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StoreDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<GetCategoryResponse> getCategories() {
        log.info("[StoreDao.getCategories]");
        String sql = "SELECT DISTINCT category FROM store WHERE status = 'active'";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new GetCategoryResponse(
                        rs.getString("category"))
        );
    }

    public List<GetStoreResponse> getStoresByCategory(String category) {
        log.info("[StoreDao.getStores]");
        String sql = "SELECT name, phone_number, order_place, min_order_price, image, category, rating " +
                "FROM store " +
                "WHERE category = :category and status = 'active'";
        Map<String, Object> param = Map.of("category", category);
        return jdbcTemplate.query(sql, param,
                (rm, rowNum) -> new GetStoreResponse(
                        rm.getString("name"),
                        rm.getString("phone_number"),
                        rm.getString("order_place"),
                        rm.getLong("min_order_price"),
                        rm.getString("image"),
                        rm.getString("category"),
                        rm.getDouble("rating"))
                );
    }
}

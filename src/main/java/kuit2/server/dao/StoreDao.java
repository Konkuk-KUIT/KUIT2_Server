package kuit2.server.dao;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import kuit2.server.dto.store.GetCategoryResponse;
import kuit2.server.dto.store.GetDetailedStoreResponse;
import kuit2.server.dto.store.GetStoreResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class StoreDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StoreDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<GetStoreResponse> getStores(long categoryId) {
        String sql = "select store_name, min_order_price from store "
                + "where store_id in ("
                + "select store_id from relation_store_category "
                + "where store_id = :category_id)";

        Map<String, Object> param = Map.of("category_id", categoryId);
        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetStoreResponse(
                        rs.getString("store_name"),
                        rs.getInt("min_order_price")
                )
        );
    }

    public GetDetailedStoreResponse getStore(long storeId) {
        String sql = "select store_name, opening_time, closing_time, store_address, store_number, store_description "
                + "from store where store_id = :store_Id";

        Map<String, Object> param = Map.of("store_id", storeId);
        return jdbcTemplate.queryForObject(sql, param, GetDetailedStoreResponse.class);
    }

    public List<GetCategoryResponse> getCategories() {
        String sql = "select category_name from category";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new GetCategoryResponse(
                        rs.getString("category_name")
                )
        );
    }
}

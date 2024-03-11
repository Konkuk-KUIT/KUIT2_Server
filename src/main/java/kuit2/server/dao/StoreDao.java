package kuit2.server.dao;

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


    public List<GetStoreResponse> getStores(String storeName, String category, Integer minimumPrice, String address) {
        String sql = "select store_name, category, minimumPrice, address from stores " +
                "where store_name like :store_name and category like :category " +
                "and minimumPrice like :minimumPrice and address like :address";

        Map<String, Object> param = Map.of(
                "storeName", "%" + storeName + "%",
                "category", "%" + category + "%",
                "minimumPrice", "%" + minimumPrice + "%",
                "address", "%" + address + "%");

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetStoreResponse(
                        rs.getString("storeName"),
                        rs.getString("category"),
                        rs.getInt("minimumPrice"),
                        rs.getString("address")
        ));
    }

}
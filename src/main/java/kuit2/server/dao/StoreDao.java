package kuit2.server.dao;

import kuit2.server.dto.store.GetStoreListResponse;
import kuit2.server.dto.store.GetStoreResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class StoreDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StoreDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<GetStoreResponse> getStores(String name, String category, int minDeliveryPrice, String status) {
        String sql = "select name, category, address, profile_image, phone_number, min_delivery_price,status from store " +
                "where name like :name and category like :category and min_delivery_price<=:min_delivery_price and status=:status";

        Map<String, Object> param = Map.of(
                "name", "%" + name + "%",
                "category", "%" + category + "%",
                "min_delivery_price", minDeliveryPrice,
                "status", status);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetStoreResponse(
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("address"),
                        rs.getString("profile_image"),
                        rs.getString("status"),
                        rs.getInt("min_delivery_price"),
                        rs.getString("status")
                        )
        );
    }

    public GetStoreListResponse getStoreList(Long lastId) {
        String rowCountSql = "select count(*) from store";
        int rowCount = jdbcTemplate.queryForObject(rowCountSql, new HashMap<>(), Integer.class);

        String sql = "select name, category, address, profile_image, phone_number, min_delivery_price, status " +
                "from store where store_id > :store_id limit 2";

        Map<String, Object> param = Map.of(
                "store_id", lastId
        );

        List<GetStoreResponse> storeResponseList = jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetStoreResponse(
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("address"),
                        rs.getString("profile_image"),
                        rs.getString("phone_number"),
                        rs.getInt("min_delivery_price"),
                        rs.getString("status")
            )
        );

        lastId += storeResponseList.size();
        boolean hasNext = lastId < rowCount;

        return new GetStoreListResponse(storeResponseList, hasNext, lastId);


    }
}

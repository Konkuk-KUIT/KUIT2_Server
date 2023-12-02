package kuit2.server.dao;

import kuit2.server.dto.store.GetStoreInfoResponse;
import kuit2.server.dto.store.GetStoreListResponse;
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


    public List<GetStoreInfoResponse> getStoreInfo(Integer storeId) {
        String sql = "SELECT store_name, load_address, min_order_price, star, food_name" +
                " FROM Store AS A LEFT OUTER JOIN Menu AS B" +
                " ON A.store_id = B.store_id" +
                " WHERE store_id=:store_id;";
        Map<String, Object> param = Map.of(
                "store_id", storeId);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetStoreInfoResponse(
                        rs.getString("store_name"),
                        rs.getString("load_address"),
                        rs.getInt("min_order_price"),
                        rs.getDouble("star"),
                        rs.getString("food_name"))
        );
    }

    public List<GetStoreResponse> searchStore (String storeName, Integer page, Integer rows) {
        String sql = "SELECT store_id, store_name FROM Store WHERE store_name like :store_name limit :start,:rows" ;
        Map<String, Object> param = Map.of(
                "store_name","%" +  storeName + "%",
                "start",page,
                "rows",rows);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetStoreResponse(
                        rs.getInt("store_id"),
                        rs.getString("store_name")
                        )
        );
    }


    public List<GetStoreListResponse> getStoreListByClassification (String classification, Integer page, Integer rows) {
        String sql = "SELECT store_id, store_name FROM Store WHERE classification=:classification limit :start,:rows" ;
        Map<String, Object> param = Map.of(
                "classification",classification,
                "start", (page-1)*rows,
                "rows", rows);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetStoreListResponse(
                        rs.getLong("store_id"),
                        rs.getString("store_name")
                )
        );
    }

    public List<GetStoreListResponse> getStoreListByMinPrice (String classification, Integer minOrderPrice) {
        String sql = "SELECT store_id, store_name FROM Store WHERE classification=:classification AND min_order_price<=:min_order_price" ;
        Map<String, Object> param = Map.of(
                "classification",classification,
                "min_order_price", minOrderPrice
                );

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetStoreListResponse(
                        rs.getLong("store_id"),
                        rs.getString("store_name")
                )
        );
    }

}

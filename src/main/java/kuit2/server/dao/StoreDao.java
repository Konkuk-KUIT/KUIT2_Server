package kuit2.server.dao;

import kuit2.server.dto.store.GetStoreResponse;
import kuit2.server.dto.store.PostStoreRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
public class StoreDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StoreDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    // DB조회를 통한 중복확인
    public boolean hasDuplicatedStoreName(String store_name){
        String sql = "select exists(select store_name from Stores where store_name=:store_name and status in ('active', 'inactive'))";
        Map<String, Object> param = Map.of("store_name", store_name);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }


    public long createStore(PostStoreRequest postStoreRequest) {
        String sql = "insert into Stores(address, phone_number, running_time, store_name)" +
                "values(:address, :phoneNumber, :runningTime, :storeName)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(postStoreRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public int modifyStoreStatus_inactive(long storeId) {
        String sql = "update Stores set status=:status where store_id=:store_id";
        Map<String, Object> param = Map.of(
                "status", "inactive",
                "store_id", storeId);
        return jdbcTemplate.update(sql, param);
    }


    public int modifyStorename(long storeId, String storename) {
        String sql = "update Stores set storename=:storename where store_name=:store_name";
        Map<String, Object> param = Map.of(
                "storename", storename,
                "store_id", storeId);

        return jdbcTemplate.update(sql, param);
    }


    public List<GetStoreResponse> getStores(String storename, String status) {
        String sql = "select store_name, running_time, address, profile_image, phone_number, status from Stores" +
                "where store_name=:storename, status=:status";

        Map<String, Object> param = Map.of(
                "storename", storename,
                "status", status);

        return jdbcTemplate.query(sql, param, (rs, rowNum) -> new GetStoreResponse(
                rs.getString("store_name"),
                rs.getString("running_time"),
                rs.getString("address"),
                rs.getString("profile_image"),
                rs.getString("phone_number"),
                rs.getString("status")));
    }


    public List<GetStoreResponse> getStoresByPage(String storename, String status, long page) {
        String sql = "select store_name, running_time, address, profile_image, phone_number, status from Stores" +
                "where store_name=:storename, status=:status limit 20 offset page:=page";           // 한번에 20개의 데이터 조회
        // 한 페이지당 20개씩 끊기도록 구현하고 싶었지만 방법을 아직 모르겠습니다.

        Map<String, Object> param = Map.of("storename", storename, "status", status, "page", page-1);

        return jdbcTemplate.query(sql, param, (rs, rowNum) -> new GetStoreResponse(
                rs.getString("store_name"),
                rs.getString("running_time"),
                rs.getString("address"),
                rs.getString("profile_image"),
                rs.getString("phone_number"),
                rs.getString("status")
        ));
    }
}

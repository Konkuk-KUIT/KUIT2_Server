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

    public long createStore(PostStoreRequest postStoreRequest) {
        String sql = "insert into store(name, category, address, phone, content) VALUES (:name, :category, :address, :phone, :content)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(postStoreRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<GetStoreResponse> getStoresByName(String name, String status) {
        String sql = "select name, category, address, phone, content from store where name like :name and status=:status";
        Map<String, Object> param = Map.of(
                "name", "%" + name + "%",
                "status", status);

        return jdbcTemplate.query(sql, param, ((rs, rowNum) ->
                new GetStoreResponse(
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("content")
                ))
        );
    }

    public List<GetStoreResponse> getStoresByCategory(String category, String status){
        String sql = "select name, category, address, phone, content from store where category=:category and status=:status";
        Map<String, Object> param = Map.of(
                "category", category,
                "status", status
        );
        return jdbcTemplate.query(sql,param, ((rs, rowNum) ->
                new GetStoreResponse(
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("content")
                )));
    }

    public int modifyStoreStatus_deleted(long storeId) {
        String sql = "update store set status=:status where store_id=:store_id";
        Map<String, Object> param = Map.of(
                "status", "deleted",
                "store_id", storeId);
        return jdbcTemplate.update(sql, param);
    }
}

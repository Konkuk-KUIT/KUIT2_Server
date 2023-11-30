package kuit2.server.dao;

import kuit2.server.dto.store.GetStoreResponse;
import kuit2.server.dto.store.PostStoreRequest;
import kuit2.server.dto.user.GetUserResponse;
import kuit2.server.dto.user.PostUserRequest;
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

    public boolean hasDuplicateName(String name) {
        String sql = "select exists(select name from store where name=:name and status in ('active', 'dormant'))";
        Map<String, Object> param = Map.of("name", name);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public long createStore(PostStoreRequest postStoreRequest) {
        String sql = "insert into store(name, address, phone, category, min_delivery_price) " +
                "values(:name, :address, :phoneNumber, :category, :minDeliveryPrice)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(postStoreRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<GetStoreResponse> getStores(String category, String status) {
        String sql = "select name, category, address, phone, min_delivery_price, status from store " +
                "where category like :category and status=:status";

        Map<String, Object> param = Map.of(
                "category", "%" + category + "%",
                "status", status);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetStoreResponse(
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getInt("min_delivery_price"),
                        rs.getString("status")
                )
        );
    }
}

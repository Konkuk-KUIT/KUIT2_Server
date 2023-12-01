package kuit2.server.dao;

import kuit2.server.dto.address.PostAddressRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
public class AddressDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AddressDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    public boolean hasCategoryValue(int category) {
        String sql = "select exists(select address from address where category=:category and status in ('active', 'dormant'))";
        Map<String, Object> param = Map.of("category", category);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public long createAddress(PostAddressRequest postAddressRequest) {
        String sql = "insert into address(user_id, address, category) " +
                "values(:userId, :address, :category)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(postAddressRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}

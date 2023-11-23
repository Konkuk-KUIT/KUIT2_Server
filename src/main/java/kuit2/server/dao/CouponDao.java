package kuit2.server.dao;

import kuit2.server.dto.coupon.GetCouponResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class CouponDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CouponDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<GetCouponResponse> getCoupons(long userId) {
        log.info("CouponDao.getCoupons");
        String sql = "SELECT c.name, c.discount_price, c.min_order_price, c.deadline, c.status " +
                "FROM coupon c JOIN user u ON c.user_id = u.user_id " +
                "WHERE u.user_id = :user_id";
        Map<String, Object> param = Map.of("user_id", userId);
        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetCouponResponse(
                        rs.getString("name"),
                        rs.getLong("discount_price"),
                        rs.getLong("min_order_price"),
                        rs.getString("deadline"),
                        rs.getString("status"))
                );
    }
}

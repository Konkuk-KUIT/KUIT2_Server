package kuit2.server.dao;

import kuit2.server.dto.coupon.GetCouponResponse;
import kuit2.server.dto.coupon.PostCouponRequest;
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
public class CouponDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CouponDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<GetCouponResponse> getCoupons(long userId) {
        log.info("CouponDao.getCoupons");
        String sql = "SELECT c.code, c.name, c.discount_price, c.min_order_price, c.deadline, c.status " +
                "FROM coupon c JOIN user u ON c.user_id = u.user_id " +
                "WHERE u.user_id = :user_id and c.status = :status";
        Map<String, Object> param = Map.of("user_id", userId, "status", "active");
        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetCouponResponse(
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getLong("discount_price"),
                        rs.getLong("min_order_price"),
                        rs.getString("deadline"),
                        rs.getString("status"))
        );
    }

    public long addCoupon(PostCouponRequest postCouponRequest) {
        log.info("CouponDao.addCoupon");
        String sql = "INSERT INTO coupon(code, name, discount_price, min_order_price, deadline, user_id) " +
                "VALUES(:code, :name, :discountPrice, :minOrderPrice, :deadline, :userId)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(postCouponRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public int useCoupon(long userId, long couponId) {
        log.info("CouponDao.useCoupon");
        String sql = "UPDATE coupon SET status=:status where user_id=:user_id and coupon_id=:coupon_id";
        Map<String, Object> param = Map.of("status", "used","user_id", userId, "coupon_id", couponId);
        return jdbcTemplate.update(sql, param);
    }
}

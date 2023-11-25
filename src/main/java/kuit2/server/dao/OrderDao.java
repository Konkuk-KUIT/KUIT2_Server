package kuit2.server.dao;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.sql.DataSource;
import kuit2.server.dto.order.GetDetailedOrderResponse;
import kuit2.server.dto.order.GetOrderResponse;
import kuit2.server.dto.order.PostOrderRequest;
import kuit2.server.dto.order.PostOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class OrderDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<GetOrderResponse> getOrders(long userId) {
        String sql = "select i.order_time, i.order_type, s.store_name, m.menu_name "
                + "from order_info as i join detail_order as d on i.order_id=d.order_id "
                + "join menu as m on d.menu_id=m.menu_id "
                + "join store as s on s.store_id=i.store_id "
                + "where user_id=:user_id";

        Map<String, Object> param = Map.of("user_id", userId);
        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetOrderResponse(
                        rs.getString("order_time"),
                        rs.getString("order_type"),
                        rs.getString("store_name"),
                        rs.getString("menu_name")
                ));
    }

    public GetDetailedOrderResponse getOrder(long orderId) {
        String sql = "select i.order_type, i.order_time, i.price_order, i.discount_price, i.payment_type, s.store_name, m.menu_name "
                + "from order_info as i join detail_order as d on i.order_id=d.order_id "
                + "join menu as m on d.menu_id=m.menu_id "
                + "join store as s on s.store_id=i.store_id "
                + "where order_id=:order_id";

        Map<String, Object> param = Map.of("order_id", orderId);    //쿼리 실행시 변수대체
        return jdbcTemplate.queryForObject(sql, param, GetDetailedOrderResponse.class);
    }

    public long order(PostOrderRequest postOrderRequest) {
        String sql = "insert into order(order_type, order_time, price_order, discount_price, payment_type, "
                + "order_status, created_at, updated_at, store_id, user_id) "
                        + "values(:orderType, :orderTime, :priceOrder, :discountPrice, :paymentType, "
                + ":orderStatus, :createdAt, :updatedAt, :storeId, :userId)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(postOrderRequest);    //쿼리
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}

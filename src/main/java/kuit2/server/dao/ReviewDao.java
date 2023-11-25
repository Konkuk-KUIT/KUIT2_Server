package kuit2.server.dao;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.sql.DataSource;
import kuit2.server.dto.review.GetReviewResponse;
import kuit2.server.dto.review.PostReviewRequest;
import kuit2.server.dto.review.PostReviewResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ReviewDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ReviewDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<GetReviewResponse> getStoreReviews(long storeId) {
        String sql = "select s.store_name, r.review_content, r.review_score, m.menu_name "
                + "from review as r join order_info as i on r.order_id=i.order_id "
                + "join detail_order as d on i.order_id=d.order_id "
                + "join menu as m on m.menu_id = d.menu_id "
                + "join store as s on m.store_id=s.store_id "
                + "where i.store_id=:store_id";
        Map<String, Object> param = Map.of("store_id", storeId);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetReviewResponse(
                        rs.getString("store_name"),
                        rs.getString("review_content"),
                        rs.getDouble("review_score"),
                        rs.getString("menu_name")
                ));
    }

    public List<GetReviewResponse> getUserReviews(long userId) {
        String sql = "select s.store_name, r.review_content, r.review_score, m.menu_name "
                + "from review as r join order_info as i on r.order_id=i.order_id "
                + "join detail_order as d on i.order_id=d.order_id "
                + "join menu as m on m.menu_id = d.menu_id "
                + "join store as s on m.store_id=s.store_id "
                + "where i.user_id=:user_id";
        Map<String, Object> param = Map.of("user_id", userId);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetReviewResponse(
                        rs.getString("store_name"),
                        rs.getString("review_content"),
                        rs.getDouble("review_score"),
                        rs.getString("menu_name")
                ));
    }

    public long writeReview(PostReviewRequest postReviewRequest) {
        String sql = "insert into review(review_content, review_score) "
                + "values(:review_content, :review_score)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(postReviewRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}

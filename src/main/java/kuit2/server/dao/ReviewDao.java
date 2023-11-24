package kuit2.server.dao;

import kuit2.server.dto.user.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Repository
public class ReviewDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ReviewDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<GetReviewResponse> getReviewsByUserId(long userId) {
        // 1단계: 리뷰 및 레스토랑 정보 조회

        String reviewSql = "SELECT r.review_id, r.star_count, r.body_text, r.boss_comment, rest.name " +
                "FROM baemin_db.review r " +
                "JOIN baemin_db.restaurant rest ON r.restaurant_id = rest.restaurant_id " +
                "WHERE r.user_id = :userId AND r.status = 'active'";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);

        List<GetReviewResponse> reviews = jdbcTemplate.query(reviewSql, parameters, (rs, rowNum) -> {
            GetReviewResponse response = new GetReviewResponse();
            response.setReviewId(rs.getLong("review_id"));
            response.setRestaurantName(rs.getString("name"));
            response.setStarCount(rs.getInt("star_count"));
            response.setBodyText(rs.getString("body_text"));
            response.setBossComment(rs.getString("boss_comment"));
            response.setReviewImages(new ArrayList<>());
            response.setReviewMenus(new ArrayList<>());
            return response;
        });

        // 맵을 사용하여 review_id 별로 리뷰 객체를 저장
        Map<Long, GetReviewResponse> reviewsMap = new HashMap<>();
        reviews.forEach(r -> reviewsMap.put(r.getReviewId(), r));

        //리뷰 이미지 조회
        String imageSql = "SELECT image_url, review_id FROM baemin_db.review_image WHERE review_id IN (:reviewIds)";
        List<Map<String, Object>> imageRows = jdbcTemplate.queryForList(imageSql, Collections.singletonMap("reviewIds", reviewsMap.keySet()));
        imageRows.forEach(row -> {
            Long reviewId = (Long) row.get("review_id");
            GetReviewResponse review = reviewsMap.get(reviewId);
            if (review != null) {
                ReviewImage image = new ReviewImage();
                image.setImageUrl((String) row.get("image_url"));
                review.getReviewImages().add(image);
            }
        });

        //리뷰 메뉴 조회
        String menuSql = "SELECT rm.review_id, m.name, rm.is_recommendable " +
                "FROM baemin_db.review_menu rm " +
                "JOIN baemin_db.menu m ON rm.menu_id = m.menu_id " +
                "WHERE rm.review_id IN (:reviewIds)";
        List<Map<String, Object>> menuRows = jdbcTemplate.queryForList(menuSql, Collections.singletonMap("reviewIds", reviewsMap.keySet()));
        menuRows.forEach(row -> {
            Long reviewId = (Long) row.get("review_id");
            GetReviewResponse review = reviewsMap.get(reviewId);
            if (review != null) {
                ReviewMenu menu = new ReviewMenu();
                menu.setMenuName((String) row.get("name"));
                menu.setIsRecommendable(1);
                review.getReviewMenus().add(menu);
            }
        });

        return new ArrayList<>(reviewsMap.values());
    }
}


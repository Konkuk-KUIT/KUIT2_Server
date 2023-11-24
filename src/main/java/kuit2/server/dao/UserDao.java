package kuit2.server.dao;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static kuit2.server.service.UserService.GRADES;

@Slf4j
@Repository
public class UserDao {
    private static final String[] GRADES = {"고마운분", "귀한분", "더귀한분", "천생연분"};


    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public boolean hasDuplicateEmail(String email) {
        String sql = "select exists(select email from user where email=:email and status in ('active', 'dormant'))";
        Map<String, Object> param = Map.of("email", email);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }


    public boolean hasDuplicateNickName(String nickname) {
        String sql = "select exists(select email from user where nickname=:nickname and status in ('active', 'dormant'))";
        Map<String, Object> param = Map.of("nickname", nickname);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public long createUser(PostUserRequest postUserRequest) {
        String sql = "insert into user(user_id,email, password, phone_number, nickname, profile_image) " +
                "values(:user_id, :email, :password, :phoneNumber, :nickname, :profileImage)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(postUserRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public int modifyUserStatus_dormant(long userId) {
        String sql = "update user set status=:status where user_id=:user_id";
        Map<String, Object> param = Map.of(
                "status", "dormant",
                "user_id", userId);
        return jdbcTemplate.update(sql, param);
    }

    public int modifyUserStatus_deleted(long userId) {
        String sql = "update user set status=:status where user_id=:user_id";
        Map<String, Object> param = Map.of(
                "status", "deleted",
                "user_id", userId);
        return jdbcTemplate.update(sql, param);
    }

    public int modifyNickname(long userId, String nickname) {
        String sql = "update user set nickname=:nickname where user_id=:user_id";
        Map<String, Object> param = Map.of(
                "nickname", nickname,
                "user_id", userId);
        return jdbcTemplate.update(sql, param);
    }

    public List<GetUserResponse> getUsers(String nickname, String email, String status) {
        String sql = "select email, phone_number, nickname, profile_image, status from user " +
                "where nickname like :nickname and email like :email and status=:status";

        Map<String, Object> param = Map.of(
                "nickname", "%" + nickname + "%",
                "email", "%" + email + "%",
                "status", status);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetUserResponse(
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("nickname"),
                        rs.getString("profile_image"),
                        rs.getString("grade"),
                        rs.getString("status"),
                        rs.getInt("order_count")
                )
        );
    }

    public long getUserIdByEmail(String email) {
        String sql = "select user_id from user where email=:email and status='active'";
        Map<String, Object> param = Map.of("email", email);
        return jdbcTemplate.queryForObject(sql, param, long.class);
    }

    public String getEmailByUserId(long userId){
        String sql = "select email from user where user_id=:user_id and status='active'";
        Map<String, Object> param =Map.of("user_id",userId);
        return jdbcTemplate.queryForObject(sql,param,String.class);
    }


    public String getPasswordByUserId(long userId) {
        String sql = "select password from user where user_id=:user_id and status='active'";
        Map<String, Object> param = Map.of("user_id", userId);
        return jdbcTemplate.queryForObject(sql, param, String.class);
    }


    public String getGradeByUserId(long userId) {
        String sql = "select grade from user where user_id=:user_id and status='active'";
        Map<String, Object> param = Map.of("user_id", userId);
        return jdbcTemplate.queryForObject(sql, param, String.class);
    }

    public int getOrderCount(long userId) {
        String sql = "SELECT order_count FROM user WHERE user_id = :userId";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    /**
     *  등급 증가
     * */
    public String increaseGrade(long userId){
        int orderCount = getOrderCountByUserId(userId);
        if (orderCount % 5 == 0) { // 등급을 변경해야 하는 경우에만 실행
            int gradeIndex = orderCount / 5 - 1; // orderCount가 5, 10, 15, ...일 때 gradeIndex가 0, 1, 2, ...가 되도록 조정
            gradeIndex = Math.min(gradeIndex, GRADES.length - 1); // 배열 범위를 넘어가지 않도록 조정
            String newGrade = GRADES[gradeIndex];

            String sql = "UPDATE user SET grade = :newGrade WHERE user_id = :userId";
            Map<String, Object> params = new HashMap<>();
            params.put("newGrade", newGrade);
            params.put("userId", userId);
            jdbcTemplate.update(sql, params);

            return newGrade;
        }
        return null;
    }

    public int getOrderCountByUserId(long userId) {
        String sql = "select order_count from user where user_id=:user_id and status='active'";
        Map<String, Object> param = Map.of("user_id", userId);
        return jdbcTemplate.queryForObject(sql, param, int.class);
    }

    /**
     *주문수 증가
     */
    public int increaseOrderCount(long userId){
        String sql="update user set order_count = order_count + 1 WHERE user_id = :userId";
        Map<String, Object> param = Map.of("userId", userId);
        return jdbcTemplate.update(sql, param);//업데이트 쿼리는 이거 사용해야함
    }



}
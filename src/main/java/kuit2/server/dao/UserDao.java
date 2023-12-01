package kuit2.server.dao;

import kuit2.server.dto.user.GetUserResponse;
import kuit2.server.dto.user.GetUserResponse2;
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
public class UserDao {

    //7-8주차 JdbcTemplate 강의 참조
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
        String sql = "insert into user(email, password, phone_number, nickname, profile_image, login_type) " +
                "values(:email, :password, :phoneNumber, :nickname, :profileImage, :login_type)";

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

    public GetUserResponse2 getUsers(String nickname, String email, String status, long lastId) {
        String countSql = "SELECT COUNT(*) AS total_users FROM user " +
                "WHERE user_id > :user_id " +
                "AND nickname LIKE :nickname " +
                "AND email LIKE :email " +
                "AND status = :status";

        String sql = "select user_id, email, phone_number, nickname, profile_image, status from user " +
                "where user_id > :user_id and nickname like :nickname and email like :email and status=:status "+
                "limit 10";

        Map<String, Object> param = Map.of(
                "nickname", "%" + nickname + "%",
                "email", "%" + email + "%",
                "status", status,
                "user_id", lastId);

        int totalUsers = jdbcTemplate.queryForObject(countSql, param, Integer.class);

        //이게 리스트 만들어주는 건가?
        List<GetUserResponse> userList = jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetUserResponse(
                        rs.getString("user_id"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("nickname"),
                        rs.getString("profile_image"),
                        rs.getString("status")));

        long newLastId = lastId + userList.size();
        boolean hasNext = newLastId < totalUsers;
        return new GetUserResponse2(userList, newLastId, hasNext);

    }

    public long getUserIdByEmail(String email) {
        String sql = "select user_id from user where email=:email and status='active'";
        Map<String, Object> param = Map.of("email", email);
        return jdbcTemplate.queryForObject(sql, param, long.class);
    }


    public String getPasswordByUserId(long userId) {
        String sql = "select password from user where user_id=:user_id and status='active'";
        Map<String, Object> param = Map.of("user_id", userId);
        return jdbcTemplate.queryForObject(sql, param, String.class);
    }
}
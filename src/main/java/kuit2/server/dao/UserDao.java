package kuit2.server.dao;

import kuit2.server.dto.user.GetUserResponse;
import kuit2.server.dto.user.PostUserRequest;
import kuit2.server.dto.user.PutUserRequest;
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

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public boolean hasDuplicateEmail(String email) {
        String sql = "select exists(select email from users where email=:email and status in ('active', 'dormant'))";
        Map<String, Object> param = Map.of("email", email);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public boolean hasDuplicateUserName(String userName) {
        String sql = "select exists(select email from users where userName=:userName and status in ('active', 'dormant'))";
        Map<String, Object> param = Map.of("userName", userName);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public long createUser(PostUserRequest postUserRequest) {
        String sql = "insert into users(email, password, phone, userName) " +
                "values(:email, :password, :phone, :userName)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(postUserRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public int modifyUserStatus_dormant(long userId) {
        String sql = "update users set status=:status where userId=:userId";
        Map<String, Object> param = Map.of(
                "status", "dormant",
                "userId", userId);
        return jdbcTemplate.update(sql, param);
    }

    public int modifyUserStatus_deleted(long userId) {
        String sql = "update users set status=:status where userId=:userId";
        Map<String, Object> param = Map.of(
                "status", "deleted",
                "userId", userId);
        return jdbcTemplate.update(sql, param);
    }

    public int modifyUserName(long userId, String userName) {
        String sql = "update users set userName=:userName where userId=:userId";
        Map<String, Object> param = Map.of(
                "userName", userName,
                "userId", userId);
        return jdbcTemplate.update(sql, param);
    }

    public int modifyUser(long userId, PutUserRequest putUserRequest) {
        String sql = "update users set email=:email, password=:password, phone=:phone, " +
                "userName=:userName where userId=:userId";

        // PutUserRequest 객체에서 값을 추출하여 Map에 추가
        Map<String, Object> param = Map.of(
                "email", putUserRequest.getEmail(),
                "password", putUserRequest.getPassword(),
                "phone", putUserRequest.getPhone(),
                "userName", putUserRequest.getUserName(),
                "userId", userId
        );

        return jdbcTemplate.update(sql, param);
    }

    public List<GetUserResponse> getUsers(String userName, String email, String status) {
        String sql = "select email, phone, userName, status from users " +
                "where userName like :userName and email like :email and status=:status";

        Map<String, Object> param = Map.of(
                "userName", "%" + userName + "%",
                "email", "%" + email + "%",
                "status", status);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetUserResponse(
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("userName"),
                        rs.getString("status"))
        );
    }

    public long getUserIdByEmail(String email) {
        String sql = "select userId from users where email=:email and status='active'";
        Map<String, Object> param = Map.of("email", email);
        return jdbcTemplate.queryForObject(sql, param, long.class);
    }


    public String getPasswordByUserId(long userId) {
        String sql = "select password from users where userId=:userId and status='active'";
        Map<String, Object> param = Map.of("userId", userId);
        return jdbcTemplate.queryForObject(sql, param, String.class);
    }

    public void updateRefreshToken(long userId, String refreshToken) {
        String sql = "update users set refreshToken = :refreshToken where userId = :userId";
        Map<String, Object> params = Map.of("userId", userId, "refreshToken", refreshToken);
        jdbcTemplate.update(sql, params);
    }

    public boolean isRefreshTokenValid(String refreshToken, long userId) {
        String sql = "select count(*) from users where userId=:userId and refreshToken=:refreshToken and status='active'";
        Map<String, Object> param = Map.of("userId", userId, "refreshToken", refreshToken);
        Integer count = jdbcTemplate.queryForObject(sql, param, Integer.class);
        return count != null && count > 0;
    }
}
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
        String sql = "select exists(select user_email from users where user_email=:email and user_status in ('일반','휴면'))";
        Map<String, Object> param = Map.of("email",email);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public boolean hasDuplicateNickName(String nickname) {
        String sql = "select exists(select user_nickname from users where user_nickname=:nickname and user_status in ('일반','휴면'))";
        Map<String, Object> param = Map.of("nickname",nickname);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public long createUser(PostUserRequest postUserRequest) {
        String sql = "insert into users(user_email, user_password, user_phonenumber, user_nickname, user_name, user_currentaddress, user_imageurl)" +
                "values(:email, :password, :phoneNumber, :nickname,:name, :currentAddress,:profileImage)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(postUserRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param,keyHolder);
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

    public int modifyNickname(long userId, String nickName) {
        String sql = "update users set user_nickname=:nickName where user_id=:userId";
        Map<String, Object> param = Map.of(
                "nickName",nickName,
                "userId",userId
        );
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
                        rs.getString("status"))
        );
    }

    public long getUserIdByEmail(String email) {
        String sql = "select user_id from users where user_email=:email and user_status='일반'";
        Map<String, Object> param = Map.of("email", email);
        return jdbcTemplate.queryForObject(sql, param, long.class);
    }


    public String getPasswordByUserId(long userId) {
        String sql = "select user_password from users where user_id=:userId and user_status='일반'";
        Map<String, Object> param = Map.of("userId", userId);
        return jdbcTemplate.queryForObject(sql, param, String.class);
    }
}
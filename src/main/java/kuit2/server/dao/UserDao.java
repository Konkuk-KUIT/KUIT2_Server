package kuit2.server.dao;

import kuit2.server.dto.store.GetUserOrderListResponse;
import kuit2.server.dto.user.*;
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
        String sql = "select exists(select email from User where email=:email and status in ('active', 'dormant'))";
        Map<String, Object> param = Map.of("email", email);
        //jdbcTemplate.queryForObject(sql, param, boolean.class)
        return Boolean.TRUE.equals(0);
    }

    public boolean hasDuplicateNickName(String nickname) {
        String sql = "select exists(select email from user where nickname=:nickname and status in ('active', 'dormant'))";
        Map<String, Object> param = Map.of("nickname", nickname);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public long createUser(PostUserRequest postUserRequest) {
        String sql = "insert into User(email, password, phone_number, nickname, profile_image, status) " +
                "values(:email, :password, :phoneNumber, :nickname, :profileImage, 'active')";

        SqlParameterSource param = new BeanPropertySqlParameterSource(postUserRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        sql = "select user_id from user where email=:email";
        Map<String, Object> param1 = Map.of("email", postUserRequest.getEmail());
        return jdbcTemplate.queryForObject(sql, param1, Integer.class);
        //return Objects.requireNonNull(keyHolder.getKey()).longValue();
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
                        rs.getString("status"))
        );
    }

    public long getUserIdByEmail(String email) {
        String sql = "select user_id from user where email=:email and status='active'";
        Map<String, Object> param = Map.of("email", email);
        return jdbcTemplate.queryForObject(sql, param, long.class);
    }


    public String getPasswordByUserId(long userId) {
        String sql = "select password from User where user_id=:user_id and status='active'";
        Map<String, Object> param = Map.of("user_id", userId);
        return jdbcTemplate.queryForObject(sql, param, String.class);
    }

    public List<GetUserJjimResponse> getUserJjim(long userId) {
        String sql = "SELECT A.store_name AS store_name" +
                " FROM Store AS A LEFT OUTER JOIN JJim AS B ON A.store_id=B.store_id"+
                " WHERE B.user_id=1";
        Map<String, Object> param = Map.of("user_id", userId);
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new GetUserJjimResponse(
                        rs.getString("store_name"))  //storeName
        );
    }

    public long addAddress(long userId, PostUserAddressRequest postUserAddressRequest) {
        String sql = "insert into Address(user_id, classification, load_address, detail_address, is_now_address) " +
                "values(:user_id, :classification, :load_address, :detail_address, true)";

        //null일때 기본값 어케주지
        assert postUserAddressRequest.getDetail_address() != null;

//        Map<String, Object> param = Map.of(
//                "user_id", userId,
//                "classification", postUserAddressRequest.getClassification(),
//                "load_address", postUserAddressRequest.getLoad_address(),
//                "detail_address", postUserAddressRequest.getDetail_address());

        SqlParameterSource param = new BeanPropertySqlParameterSource(postUserAddressRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<GetUserOrderListResponse> getOrders(long userId) {
        String sql = "SELECT C.store_name, A.food_name, B.option_name, A.price"+
                "FROM Orders AS A LEFT OUTER JOIN Options AS B"+
                "ON A.option_pack_id = B.option_pack_id"+
                "LEFT OUTRE JOIN Store AS C ON A.store_id=C.store_id"+
                "WHERE A.user_id:=user_id";
        Map<String, Object> param = Map.of("user_id", userId);
        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetUserOrderListResponse(
                        rs.getString("store_name"),
                        rs.getString("food_name"),
                        rs.getString("option_name"),
                        rs.getInt("price")
                        )
        );
    }


    // jwt 저장
    public void saveJWT(long userId, String jwt){
        String sql = "update user set jwt=:jwt where user_id =:user_id";
        Map<String, Object> param = Map.of(
                "jwt", jwt,
                "user_id",userId
                );
        jdbcTemplate.update(sql, param);
    }
}
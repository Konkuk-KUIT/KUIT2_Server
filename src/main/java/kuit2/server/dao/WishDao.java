package kuit2.server.dao;

import kuit2.server.dto.wish.GetWishListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class WishDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WishDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    public List<GetWishListResponse> getWishList(long userId) {
        log.info("WishDao.getWishList");

        String sql = "SELECT s.name, s.rating, s.min_order_price, s.image " +
                "FROM user u JOIN wish w ON u.user_id = w.user_id JOIN store s ON w.store_id = s.store_id " +
                "WHERE u.user_id = :user_id";

        Map<String, Object> param = Map.of("user_id", userId);
        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetWishListResponse(
                        rs.getString("name"),
                        rs.getDouble("rating"),
                        rs.getLong("min_order_price"),
                        rs.getString("image"))
                );
    }

    public void addWishList(long userId, long storeId) {
        log.info("WishDao.addWishList");
        String sql = "INSERT INTO WISH(user_id, store_id) VALUES(:user_id, :store_id)";
        Map<String, Object> param =  Map.of("user_id", userId, "store_id", storeId);
        jdbcTemplate.update(sql, param);
    }

    public int modifyWishStatus_active(long userId, long storeId) {
        String sql = "update wish set status=:status where user_id=:user_id and store_id=:store_id";
        Map<String, Object> param = Map.of(
                "status", "active",
                "user_id", userId,
                "store_id", storeId);
        return jdbcTemplate.update(sql, param);
    }

    public int modifyWishStatus_deleted(long userId, long storeId) {
        String sql = "update wish set status=:status where user_id=:user_id and store_id=:store_id";
        Map<String, Object> param = Map.of(
                "status", "deleted",
                "user_id", userId,
                "store_id", storeId);
        return jdbcTemplate.update(sql, param);
    }
}

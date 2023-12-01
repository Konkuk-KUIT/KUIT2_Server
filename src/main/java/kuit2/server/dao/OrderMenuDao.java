package kuit2.server.dao;

import kuit2.server.dto.user.GetCartResponse;
import kuit2.server.dto.user.MenuOption;
import kuit2.server.dto.user.PostUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class OrderMenuDao {

    private final JdbcTemplate jdbcTemplate;

    public OrderMenuDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetCartResponse> getOrderMenusByUserId(Long userId) {
        String sql =
                "SELECT m.name AS menu_name, mo.name AS option_name, mo.price AS option_price, moc.name AS option_category_name, m.price AS menu_price " +
                        "FROM baemin_db.order_menu om " +
                        "JOIN baemin_db.menu m ON om.menu_id = m.menu_id " +
                        "JOIN baemin_db.menu_option mo ON mo.menu_id = m.menu_id " +
                        "JOIN baemin_db.menu_option_category moc ON mo.menu_option_id = moc.menu_option_id " +
                        "WHERE om.user_id = ? AND om.status = 'active'";


        return jdbcTemplate.query(sql, new Object[]{userId}, new RowMapper<GetCartResponse>() {

            public GetCartResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetCartResponse response = new GetCartResponse();
                response.setMenuName(rs.getString("menu_name"));
                response.setMenuPrice(rs.getInt("menu_price"));

                MenuOption option = new MenuOption();
                option.setMenuOptionCategory(rs.getString("option_category_name"));
                option.setMenuOptionName(rs.getString("option_name"));
                option.setMenuOptionPrice(rs.getFloat("option_price"));

                response.setOptions(Arrays.asList(option)); // This is a simplification. You'll need to handle multiple options.
                return response;
            }
        });
    }
}

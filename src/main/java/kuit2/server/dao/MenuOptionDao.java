package kuit2.server.dao;

import kuit2.server.dto.menuOption.MenuOptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class MenuOptionDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MenuOptionDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<MenuOptionResponse> getMenuOptions(long storeId, long menuId) {
        log.info("MenuOptionDao.getMenuOptions");
        String sql = "SELECT mo.category, mo.name, mo.additional_price " +
                "FROM menu_option mo JOIN menu m ON m.menu_id = mo.menu_id " +
                "JOIN store s ON s.store_id = m.store_id " +
                "WHERE s.store_id = :store_id and m.menu_id = :menu_id";
        Map<String, Object> param = Map.of( "store_id", storeId, "menu_id", menuId);
        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new MenuOptionResponse(
                        rs.getString("category"),
                        rs.getString("name"),
                        rs.getLong("additional_price")
                )
                );
    }
}

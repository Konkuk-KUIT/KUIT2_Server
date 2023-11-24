package kuit2.server.dao;

import kuit2.server.dto.menu.GetMenuResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class MenuDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MenuDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<GetMenuResponse> getMenus(long storeId) {
        log.info("MenuDao.getMenus");
        String sql = "SELECT m.name, m.price, m.image, m.category " +
                "FROM menu m JOIN store s ON m.store_id = s.store_id " +
                "WHERE s.store_id = :store_id and m.status = 'active' and s.status = 'active'";
        Map<String, Object> param = Map.of("store_id", storeId);
        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetMenuResponse(
                        rs.getString("name"),
                        rs.getLong("price"),
                        rs.getString("image"),
                        rs.getString("category")
                        )
                );
    }
}

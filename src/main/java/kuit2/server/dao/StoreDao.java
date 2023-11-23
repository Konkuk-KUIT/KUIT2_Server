package kuit2.server.dao;

import kuit2.server.dto.store.GetCategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Repository
public class StoreDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StoreDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<GetCategoryResponse> getCategories() {
        log.info("[StoreDao.getCategories]");
        String sql = "SELECT DISTINCT category FROM store WHERE status = 'active'";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new GetCategoryResponse(
                        rs.getString("category"))
        );
    }
}

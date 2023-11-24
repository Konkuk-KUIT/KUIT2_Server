package kuit2.server.dao;

import kuit2.server.dto.user.GetCategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Repository
public class RestaurantDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RestaurantDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    public List<GetCategoryResponse> getCategories() {
        String sql = "select distinct category from Stores where status = 'active'";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new GetCategoryResponse(rs.getString("category"))
        );
    }
}

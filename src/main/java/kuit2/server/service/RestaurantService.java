package kuit2.server.service;

import kuit2.server.dao.RestaurantDao;
import kuit2.server.dto.restaurant.GetCategoriesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantDao restaurantDao;


    public GetCategoriesResponse getCategories() {
        log.info("[RestaurantService.getCategories]");

        return restaurantDao.getCategories();
    }
}

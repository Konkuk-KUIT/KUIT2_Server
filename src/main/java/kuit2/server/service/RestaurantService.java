package kuit2.server.service;

import kuit2.server.dao.RestaurantDao;
import kuit2.server.dto.restaurant.GetCategoriesResponse;
import kuit2.server.dto.restaurant.GetRestaurantMenuResponse;
import kuit2.server.dto.user.GetBriefRestaurantResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantDao restaurantDao;


    public GetCategoriesResponse getCategories() {
        log.info("[RestaurantService.getCategories]");

        return restaurantDao.getCategories();
    }

    public List<GetBriefRestaurantResponse> getRestaurants(long categoryId, String sortBy, String minOrderPrice) {
        log.info("[RestaurantService.getRestaurants]");

        return restaurantDao.getRestaurants(categoryId, sortBy, minOrderPrice);
    }

    public List<GetRestaurantMenuResponse> getRestaurantMenus(long restaurantId) {
        log.info("[RestaurantService.getRestaurantMenus]");

        return restaurantDao.getRestaurantMenus(restaurantId);

    }
}

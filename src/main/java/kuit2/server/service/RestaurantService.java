package kuit2.server.service;

import kuit2.server.common.exception.RestaurantException;
import kuit2.server.dao.RestaurantDao;
import kuit2.server.dto.restaurant.GetCategoriesResponse;
import kuit2.server.dto.restaurant.GetRestaurantMenuResponse;
import kuit2.server.dto.restaurant.GetMenuResponse;
import kuit2.server.dto.restaurant.GetRestaurantResponse;
import kuit2.server.dto.user.GetBriefRestaurantResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.RESTAURANT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantDao restaurantDao;


    public GetCategoriesResponse getCategories() {
        log.info("[RestaurantService.getCategories]");

        return restaurantDao.getCategories();
    }

    public GetRestaurantResponse getRestaurants(long lastId, long categoryId, String sortBy, String minOrderPrice) {
        log.info("[RestaurantService.getRestaurants]");

        return restaurantDao.getRestaurantsV2(lastId, categoryId, sortBy, minOrderPrice);
    }

    public List<GetRestaurantMenuResponse> getRestaurantMenus(long restaurantId) {
        log.info("[RestaurantService.getRestaurantMenus]");

        if(restaurantDao.getBriefRestaurantById(restaurantId) != null){
            return restaurantDao.getRestaurantMenus(restaurantId);
        }

        throw new RestaurantException(RESTAURANT_NOT_FOUND);
    }

    public GetMenuResponse getRestaurantMenu(long restaurantId, long menuId) {
        log.info("[RestaurantService.getRestaurantMenu]");

        if(restaurantDao.getBriefRestaurantById(restaurantId) != null){
            return restaurantDao.getRestaurantMenu(restaurantId, menuId);
        }

        throw new RestaurantException(RESTAURANT_NOT_FOUND);
    }
}

package kuit2.server.service;

import kuit2.server.dao.RestaurantDao;
import kuit2.server.dto.restaurant.GetRestaurantResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantDao restaurantDao;

    public List<GetRestaurantResponse> getRestaurants(String name, long minimumPrice) {
        log.info("[RestaurantService.getRestaurant]");
        return restaurantDao.getRestaurants(name, minimumPrice);
    }

    public List<GetRestaurantResponse> getRestaurantlist(long page) {
        log.info("[RestaurantService.getRestaurantlist]");
        return restaurantDao.getRestaurantlist(page);
    }
}

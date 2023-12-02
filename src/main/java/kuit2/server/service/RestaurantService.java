package kuit2.server.service;

import kuit2.server.common.exception.DatabaseException;
import kuit2.server.dao.RestaurantDao;
import kuit2.server.dto.RestaurantRequest;
import kuit2.server.dto.RestaurantUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.DATABASE_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantDao restaurantDao;

    public List<RestaurantRequest> getRestaurantsByCategory(String category) {
        log.info("[RestaurantService.getRestaurantsByCategory]");
        return restaurantDao.getRestaurantsByCategory(category);
    }

    public RestaurantRequest updateRestaurantInfo(String restaurantId, RestaurantUpdateRequest updateRequest) {
        log.info("[RestaurantService.updateRestaurantInfo]");
        RestaurantRequest restaurantRequest = new RestaurantRequest(updateRequest);
        restaurantRequest.setId(Long.parseLong(restaurantId));
        int affectedRows = restaurantDao.updateRestaurant(restaurantRequest);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
        return restaurantDao.findById(Long.parseLong(restaurantId));
    }

    public List<RestaurantRequest> getRestaurantsByName(String name) {
        log.info("[RestaurantService.getRestaurantsByName]");
        return (List<RestaurantRequest>) restaurantDao.findByName(name);
    }

    public long createRestaurant(RestaurantRequest restaurantRequest) {
        log.info("[RestaurantService.createRestaurant]");
        return restaurantDao.createRestaurant(restaurantRequest);
    }

    public void deleteRestaurant(String restaurantId) {
        log.info("[RestaurantService.deleteRestaurant]");
        int affectedRows = restaurantDao.deleteRestaurant(Long.parseLong(restaurantId));
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

}

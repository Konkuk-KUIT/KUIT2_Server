package kuit2.server.service;

import kuit2.server.dao.RestaurantDao;
import kuit2.server.dto.user.GetCategoryResponse;
import kuit2.server.dto.user.GetStoreDetailResponse;
import kuit2.server.dto.user.GetStoreResponse;
import kuit2.server.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantDao restaurantDao;
    private final JwtProvider jwtProvider;


    public List<GetCategoryResponse> getCategories() {
        log.info("[RestaurantService.getCategories]");

        return restaurantDao.getCategories();
    }

    public List<GetStoreResponse> getStoresByCategory(String category) {
        log.info("[RestaurantService.getStoresByCategory]");

        return restaurantDao.getStoresByCategory(category);
    }

    public List<GetStoreDetailResponse> getStore(String storeId) {
        log.info("[RestaurantService.getStore]");

        return restaurantDao.getStore(storeId);
    }
}

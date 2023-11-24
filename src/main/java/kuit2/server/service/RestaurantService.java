package kuit2.server.service;

import kuit2.server.dao.RestaurantDao;
import kuit2.server.dto.user.GetCategoryResponse;
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
}

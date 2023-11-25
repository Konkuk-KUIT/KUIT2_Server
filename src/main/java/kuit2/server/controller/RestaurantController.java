package kuit2.server.controller;

import kuit2.server.common.response.BaseResponse;
import kuit2.server.dao.RestaurantDao;
import kuit2.server.dto.restaurant.GetCategoriesResponse;
import kuit2.server.dto.user.GetBriefRestaurantResponse;
import kuit2.server.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    /**
     * 가게 카테고리 목록 조회
     */
    @GetMapping("/categories")
    public BaseResponse<GetCategoriesResponse> getCategories() {
        log.info("RestaurantController.getCategories");

        return new BaseResponse<>(restaurantService.getCategories());
    }

    /**
     * 음식점 목록 조회
     */
    @GetMapping("/{categoryId}")
    public BaseResponse<List<GetBriefRestaurantResponse>> getRestaurants(@PathVariable long categoryId, @RequestParam(name = "sort-by") String sortBy, @RequestParam(name = "min-order-price") String minOrderPrice) {
        log.info("RestaurantController.getRestaurants");

        return new BaseResponse<>(restaurantService.getRestaurants(categoryId, sortBy, minOrderPrice));
    }
}

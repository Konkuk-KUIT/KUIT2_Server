package kuit2.server.controller;

import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.restaurant.GetCategoriesResponse;
import kuit2.server.dto.restaurant.GetRestaurantMenuResponse;
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
    public BaseResponse<List<GetBriefRestaurantResponse>> getRestaurants(
            @PathVariable long categoryId,
            @RequestParam(name = "lastId", required = false, defaultValue = "0") long lastId,
            @RequestParam(name = "sort-by", required = false) String sortBy,
            @RequestParam(name = "min-order-price", required = false) String minOrderPrice) {

        log.info("RestaurantController.getRestaurants");

        return new BaseResponse<>(restaurantService.getRestaurants(lastId, categoryId, sortBy, minOrderPrice));
    }

    /**
     * 음식점 메뉴 조회
     */
    @GetMapping("/{restaurantId}/menus")
    public BaseResponse<List<GetRestaurantMenuResponse>> getRestaurantMenus(@PathVariable long restaurantId) {
        log.info("RestaurantController.getRestaurantMenus");

        return new BaseResponse<>(restaurantService.getRestaurantMenus(restaurantId));
    }
}

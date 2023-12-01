package kuit2.server.controller;

import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.user.GetCategoryResponse;
import kuit2.server.dto.user.GetStoreDetailResponse;
import kuit2.server.dto.user.GetStoreResponse;
import kuit2.server.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/categories")
    public BaseResponse<List<GetCategoryResponse>> getCategories() {
        log.info("[RestaurantController.getCategories]");

        return new BaseResponse<>(restaurantService.getCategories());
    }

    //@GetMapping("/categories/{categoryId}")
    public BaseResponse<List<GetStoreResponse>> getStoresByCategoryV1(
            @RequestParam(required = false, defaultValue = "") String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("[RestaurantController.getStoresByCategory]");

        return new BaseResponse<>(restaurantService.getStoresByCategoryV1(category, page, size));
    }

    @GetMapping("/categories/{categoryId}")
    public BaseResponse<List<GetStoreResponse>> getStoresByCategoryV2(
            @RequestParam(required = false, defaultValue = "") String category,
            @RequestParam(required = false) Long lastStoreId,
            @RequestParam(defaultValue = "10") int size) {
        log.info("[RestaurantController.getStoresByCategory]");

        return new BaseResponse<>(restaurantService.getStoresByCategoryV2(category, lastStoreId, size));
    }

    @GetMapping("/{storeId}")
    public BaseResponse<List<GetStoreDetailResponse>> getStore(@RequestParam(required = false, defaultValue = "") String storeId, String lastId) {
        log.info("[RestaurantController.getStore]");

        return new BaseResponse<>(restaurantService.getStore(storeId));
    }
}

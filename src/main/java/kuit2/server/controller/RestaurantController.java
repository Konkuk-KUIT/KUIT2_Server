package kuit2.server.controller;

import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.user.GetCategoryResponse;
import kuit2.server.dto.user.GetStoreDetailResponse;
import kuit2.server.dto.user.GetStoreResponse;
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

    @GetMapping("/categories")
    public BaseResponse<List<GetCategoryResponse>> getCategories() {
        log.info("[RestaurantController.getCategories]");

        return new BaseResponse<>(restaurantService.getCategories());
    }

    @GetMapping("/categories/{categoryId}")
    public BaseResponse<List<GetStoreResponse>> getStoresByCategoryV1(
            @PathVariable(required = false) String categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        log.info("[RestaurantController.getStoresByCategory]");

        return new BaseResponse<>(restaurantService.getStoresByCategoryV1(categoryId, page, size));
    }

    //@GetMapping("/categories/{categoryId}")
    public BaseResponse<List<GetStoreResponse>> getStoresByCategoryV2(
            @PathVariable(required = false) String category,
            @RequestParam(required = false, defaultValue = "0") Long lastStoreId,
            @RequestParam(defaultValue = "5") int size) {
        log.info("[RestaurantController.getStoresByCategory]");

        return new BaseResponse<>(restaurantService.getStoresByCategoryV2(category, lastStoreId, size));
    }

    @GetMapping("/{storeId}")
    public BaseResponse<List<GetStoreDetailResponse>> getStore(@RequestParam(required = false, defaultValue = "") String storeId, String lastId) {
        log.info("[RestaurantController.getStore]");

        return new BaseResponse<>(restaurantService.getStore(storeId));
    }
}

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

    @GetMapping("/categories/{categoryId}")
    public BaseResponse<List<GetStoreResponse>> getStoresByCategory(@RequestParam(required = false, defaultValue = "") String category) {
        log.info("[RestaurantController.getStoresByCategory]");

        return new BaseResponse<>(restaurantService.getStoresByCategory(category));
    }

    @GetMapping("/{storeId}")
    public BaseResponse<List<GetStoreDetailResponse>> getStore(@RequestParam(required = false, defaultValue = "") String storeId) {
        log.info("[RestaurantController.getStore]");

        return new BaseResponse<>(restaurantService.getStore(storeId));
    }
}

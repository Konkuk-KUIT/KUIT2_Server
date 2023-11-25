package kuit2.server.controller;

import kuit2.server.common.response.BaseResponse;
import kuit2.server.dao.RestaurantDao;
import kuit2.server.dto.restaurant.GetCategoriesResponse;
import kuit2.server.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

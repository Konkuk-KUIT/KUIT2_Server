package kuit2.server.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.restaurant.GetRestaurantResponse;
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
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("")
    public BaseResponse<List<GetRestaurantResponse>> getRestaurants(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "0") long minimumPrice
    ){
        log.info("[RestaurantController.getUsers]");
        return new BaseResponse<>(restaurantService.getRestaurants(name, minimumPrice));
    }

    @GetMapping("list")
    public BaseResponse<List<GetRestaurantResponse>> getRestaurantslist(
            @RequestParam(required = false, defaultValue = "") long page
    ){
        log.info("[RestaurantController.getrestaurantlist]");
        return new BaseResponse<>(restaurantService.getRestaurantlist(page));

    }
}

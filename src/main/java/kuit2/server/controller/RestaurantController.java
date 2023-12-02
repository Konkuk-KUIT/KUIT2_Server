package kuit2.server.controller;

import kuit2.server.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    // 카테고리별 레스토랑 목록 조회
    @GetMapping("/category")
    public ResponseEntity<?> getRestaurantsByCategory(
            @RequestParam(value = "sort-by", required = false) String sortBy,
            @RequestParam(value = "min-order-fee", required = false) Integer minOrderFee) {
        // 여기서 restaurantService.getRestaurantsByCategory(...)를 호출하고 결과를 반환합니다.
        return ResponseEntity.ok().body(restaurantService.getRestaurantsByCategory(sortBy, minOrderFee));
    }

    // 특정 레스토랑 정보 조회
    @GetMapping("/{restaurantId}")
    public ResponseEntity<?> getRestaurantById(@PathVariable String restaurantId) {
        // 여기서 restaurantService.getRestaurantById(...)를 호출하고 결과를 반환합니다.
        return ResponseEntity.ok().body(restaurantService.getRestaurantById(restaurantId));
    }

    // 특정 레스토랑 리뷰 조회
    @GetMapping("/{restaurantId}/review")
    public ResponseEntity<?> getReviewsByRestaurant(@PathVariable String restaurantId) {
        // 여기서 restaurantService.getReviewsByRestaurant(...)를 호출하고 결과를 반환합니다.
        return ResponseEntity.ok().body(restaurantService.getReviewsByRestaurant(restaurantId));
    }

    // 레스토랑 메뉴 조회
    @GetMapping("/menu/{restaurantId}")
    public ResponseEntity<?> getMenuByRestaurant(@PathVariable String restaurantId) {
        // 여기서 restaurantService.getMenuByRestaurant(...)를 호출하고 결과를 반환합니다.
        return ResponseEntity.ok().body(restaurantService.getMenuByRestaurant(restaurantId));
    }

    // 레스토랑에 리뷰 추가
    @PostMapping("/{restaurantId}/review")
    public ResponseEntity<?> addReviewToRestaurant(
            @PathVariable String restaurantId,
            @RequestBody ReviewDto reviewDto) {
        // 여기서 restaurantService.addReviewToRestaurant(...)를 호출하고 결과를 반환합니다.
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.addReviewToRestaurant(restaurantId, reviewDto));
    }


}

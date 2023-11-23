package kuit2.server.controller;

import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.coupon.GetCouponResponse;
import kuit2.server.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}")
public class CouponController {

    private final CouponService couponService;

    /**
     * 쿠폰 조회
     */
    @GetMapping("/coupons")
    public BaseResponse<List<GetCouponResponse>> getCoupons(@PathVariable long userId) {
        log.info("[CouponController.getCoupons]");
        return new BaseResponse<>(couponService.getCoupons(userId));
    }
}

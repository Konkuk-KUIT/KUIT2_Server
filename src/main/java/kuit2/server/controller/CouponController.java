package kuit2.server.controller;

import kuit2.server.common.exception.CouponException;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.common.response.status.BaseExceptionResponseStatus;
import kuit2.server.dto.coupon.GetCouponResponse;
import kuit2.server.dto.coupon.PostCouponRequest;
import kuit2.server.dto.coupon.PostCouponResponse;
import kuit2.server.service.CouponService;
import kuit2.server.util.BindingResultUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.*;
import static kuit2.server.util.BindingResultUtils.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/coupons")
public class CouponController {

    private final CouponService couponService;

    /**
     * 쿠폰 조회
     */
    @GetMapping("")
    public BaseResponse<List<GetCouponResponse>> getCoupons(@PathVariable long userId) {
        log.info("[CouponController.getCoupons]");
        return new BaseResponse<>(couponService.getCoupons(userId));
    }

    /**
     * 쿠폰 등록
     */
    @PostMapping("")
    public BaseResponse<PostCouponResponse> addCoupon(@PathVariable long userId,
                                                      @Validated @RequestBody PostCouponRequest postCouponRequest,
                                                      BindingResult bindingResult) {
        log.info("[CouponController.addCoupon]");
        if(bindingResult.hasErrors()) {
            throw new CouponException(INVALID_COUPON_CODE, getErrorMessages(bindingResult));
        }
        postCouponRequest.setUserId(userId);
        return new BaseResponse<PostCouponResponse>(couponService.addCoupon(postCouponRequest));
    }

    /**
     * 쿠폰 사용 (삭제)
     */
    @PatchMapping("/{couponId}/used")
    public BaseResponse<Object> modifyCouponStatus_used(@PathVariable long userId, @PathVariable long couponId) {
        log.info("[CouponController.useCoupon]");
        couponService.useCoupon(userId, couponId);
        return new BaseResponse<>(null);
    }
}

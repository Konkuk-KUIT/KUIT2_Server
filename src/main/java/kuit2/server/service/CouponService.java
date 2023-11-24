package kuit2.server.service;

import kuit2.server.dao.CouponDao;
import kuit2.server.dto.coupon.GetCouponResponse;
import kuit2.server.dto.coupon.PostCouponRequest;
import kuit2.server.dto.coupon.PostCouponResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponDao couponDao;

    public List<GetCouponResponse> getCoupons(long userId) {
        log.info("[CouponService.getCoupons]");
        return(couponDao.getCoupons(userId));
    }

    public PostCouponResponse addCoupon(PostCouponRequest postCouponRequest) {
        log.info("[CouponService.addCoupon]");
        return new PostCouponResponse(couponDao.addCoupon(postCouponRequest));
    }
}

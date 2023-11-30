package kuit2.server.service;

import kuit2.server.common.exception.DatabaseException;
import kuit2.server.common.response.status.BaseExceptionResponseStatus;
import kuit2.server.dao.CouponDao;
import kuit2.server.dto.coupon.GetCouponResponse;
import kuit2.server.dto.coupon.PostCouponRequest;
import kuit2.server.dto.coupon.PostCouponResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.*;

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

    public void useCoupon(long userId, long couponId) {
        log.info("[CouponService.useCoupon]");
        int affectedRows = couponDao.useCoupon(userId, couponId);
        if(affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }
}

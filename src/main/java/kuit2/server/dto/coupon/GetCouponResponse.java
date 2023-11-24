package kuit2.server.dto.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCouponResponse {
    private String code;
    private String name;
    private long discountPrice;
    private long minOrderPrice;
    private String deadline;
    private String status;
}

package kuit2.server.dto.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCouponResponse {
    private String code;
    private String name;
    private long discount_price;
    private long min_order_price;
    private String deadline;
    private String status;
}

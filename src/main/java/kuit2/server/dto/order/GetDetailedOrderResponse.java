package kuit2.server.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetDetailedOrderResponse {

    private String orderType;
    private String orderTime;
    private int priceOrder;
    private int discountPrice;
    private String storeName;
    private String menuName;
}
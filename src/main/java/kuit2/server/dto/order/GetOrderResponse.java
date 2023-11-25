package kuit2.server.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderResponse {

    private String orderType;
    private String orderTime;
    private String storeName;
    private String menuName;
}

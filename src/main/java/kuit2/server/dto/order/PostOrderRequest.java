package kuit2.server.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostOrderRequest {

    @NotBlank(message = "orderType: {NotBlank}")
    private String orderType;

    @NotBlank(message = "orderTime: {NotBlank}")
    private String orderTime;

    @NotBlank(message = "paymentType: {NotBlank}")
    private String paymentType;

    @NotBlank(message = "orderStatus: {NotBlank}")
    private String orderStatus;

    @NotBlank(message = "storeId: {NotBlank}")
    private Long storeId;

    @NotBlank(message = "userId: {NotBlank}")
    private Long userId;
}

package kuit2.server.controller;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.INVALID_ORDER_VALUE;
import static kuit2.server.util.BindingResultUtils.getErrorMessages;

import java.util.List;
import kuit2.server.common.exception.OrderException;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.order.GetDetailedOrderResponse;
import kuit2.server.dto.order.GetOrderResponse;
import kuit2.server.dto.order.PostOrderRequest;
import kuit2.server.dto.order.PostOrderResponse;
import kuit2.server.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문내역리스트
     */
    @GetMapping("/{userId}")
    public BaseResponse<List<GetOrderResponse>> getOrders(@PathVariable("userId") long userId) {
        log.info("[OrderController.getOrders/{userId}]");
        return new BaseResponse<>(orderService.getOrders(userId));
    }

    /**
     * 주문상세정보
     */
    @GetMapping("/{orderId}")
    public BaseResponse<GetDetailedOrderResponse> getOrder(@PathVariable("orderId") long orderId) {
        log.info("OrderController.getOrders/{orderId}]");
        return new BaseResponse<>(orderService.getOrder(orderId));
    }

    /**
     * 주문실행
     */
    @PostMapping("")
    public BaseResponse<PostOrderResponse> postOrder(@RequestBody PostOrderRequest postOrderRequest, BindingResult bindingResult) {
        log.info("[OrderController.postOrder]");
        if (bindingResult.hasErrors()) {
            throw new OrderException(INVALID_ORDER_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(orderService.order(postOrderRequest));
    }
}

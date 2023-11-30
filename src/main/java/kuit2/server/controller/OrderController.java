package kuit2.server.controller;

import kuit2.server.common.exception.OrderException;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.order.PostOrderRequest;
import kuit2.server.dto.order.PostOrderResponse;
import kuit2.server.service.OrderService;
import kuit2.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.BAD_REQUEST;
import static kuit2.server.util.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    //주문하기
    @PostMapping("")
    public BaseResponse<PostOrderResponse> order(@Validated @RequestBody PostOrderRequest postOrderRequest, BindingResult bindingResult) {
        log.info("[OrderController.order]");
        if (bindingResult.hasErrors()) {
            throw new OrderException(BAD_REQUEST, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(orderService.order(postOrderRequest));
    }
}
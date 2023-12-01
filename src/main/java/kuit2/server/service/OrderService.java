package kuit2.server.service;

import kuit2.server.dto.order.PostOrderRequest;
import kuit2.server.dto.order.PostOrderResponse;
import kuit2.server.dto.user.PostUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    public PostOrderResponse order(PostOrderRequest postOrderRequest) {
        return new PostOrderResponse(1, "주문하기 test입니다.");
    }
}

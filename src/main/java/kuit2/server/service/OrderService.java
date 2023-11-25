package kuit2.server.service;

import java.util.List;
import kuit2.server.dao.OrderDao;
import kuit2.server.dto.order.GetDetailedOrderResponse;
import kuit2.server.dto.order.GetOrderResponse;
import kuit2.server.dto.order.PostOrderRequest;
import kuit2.server.dto.order.PostOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;

    public List<GetOrderResponse> getOrders(long userId) {
        log.info("[OrderService.getOrders]");

        return orderDao.getOrders(userId);
    }

    public GetDetailedOrderResponse getOrder(long orderId) {
        log.info("[OrderService.getOrders]");

        return orderDao.getOrder(orderId);
    }

    public PostOrderResponse order(PostOrderRequest postOrderRequest) {
        log.info("[OrderService.order]");

        /**
         * validate
         */


        long orderId = orderDao.order(postOrderRequest);

        return new PostOrderResponse(orderId);
    }
}

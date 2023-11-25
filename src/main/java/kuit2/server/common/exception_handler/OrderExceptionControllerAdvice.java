package kuit2.server.common.exception_handler;

import jakarta.annotation.Priority;
import kuit2.server.common.exception.OrderException;
import kuit2.server.common.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.INVALID_ORDER_VALUE;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class OrderExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OrderException.class)
    public BaseErrorResponse handle_OrderException(OrderException e) {
        log.error("[handle_OrderException]", e);
        return new BaseErrorResponse(INVALID_ORDER_VALUE, e.getMessage());
    }
}

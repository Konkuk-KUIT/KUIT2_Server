package kuit2.server.common.exception_handler;

import jakarta.annotation.Priority;
import kuit2.server.common.exception.RestaurantException;
import kuit2.server.common.exception.UserException;
import kuit2.server.common.response.BaseExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.RESTAURANT_NOT_FOUND;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class RestaurantExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestaurantException.class)
    public BaseExceptionResponse handle_RestaurantException(RestaurantException e) {
        log.error("[handle_RestaurantException]", e);
        return new BaseExceptionResponse(RESTAURANT_NOT_FOUND, e.getMessage());
    }
}

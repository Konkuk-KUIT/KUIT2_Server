package kuit2.server.common.exception_handler;

import jakarta.annotation.Priority;
import kuit2.server.common.exception.StoreException;
import kuit2.server.common.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.INVALID_STORE_VALUE;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class StoreExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StoreException.class)
    public BaseErrorResponse handle_StoreException(StoreException e) {
        log.error("[StoreExceptionControllerAdvice.handle_StoreException]", e);
        return new BaseErrorResponse(INVALID_STORE_VALUE, e.getMessage());
    }
}

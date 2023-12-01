package kuit2.server.common.exception_handler;

import jakarta.annotation.Priority;
import kuit2.server.common.exception.AddressException;
import kuit2.server.common.exception.UserException;
import kuit2.server.common.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.INVALID_ADDRESS_VALUE;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class AddressExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AddressException.class)
    public BaseErrorResponse handle_AddressException(AddressException e) {
        log.error("[handle_AddressException]", e);
        return new BaseErrorResponse(INVALID_ADDRESS_VALUE, e.getMessage());
    }

}

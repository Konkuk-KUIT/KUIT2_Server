package kuit2.server.common.exception_handler;

import jakarta.annotation.Priority;
import kuit2.server.common.exception.UserException;
import kuit2.server.common.response.BaseExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.INVALID_USER_VALUE;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class UserExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public BaseExceptionResponse handle_UserException(UserException e) {
        log.error("[handle_UserException]", e);
        return new BaseExceptionResponse(INVALID_USER_VALUE, e.getMessage());
    }
}

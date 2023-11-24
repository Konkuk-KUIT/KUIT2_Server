package kuit2.server.common.exception_handler;

import kuit2.server.common.exception.StoreException;
import kuit2.server.common.exception.UserException;
import kuit2.server.common.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.INVALID_USER_VALUE;

@Slf4j
@RestControllerAdvice
public class TempExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public BaseErrorResponse handle_UserException(UserException e) {
        log.error("[handle_UserException]", e);
        return new BaseErrorResponse(INVALID_USER_VALUE, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public BaseErrorResponse handle_StoreException(StoreException e) {
        log.error("[handle_StoreException]", e);
        return new BaseErrorResponse(INVALID_USER_VALUE, e.getMessage());
    }


}

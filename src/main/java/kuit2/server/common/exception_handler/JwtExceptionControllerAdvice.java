package kuit2.server.common.exception_handler;

import jakarta.annotation.Priority;
import kuit2.server.common.exception.jwt.bad_request.JwtBadRequestException;
import kuit2.server.common.exception.jwt.unauthorized.JwtUnauthorizedTokenException;
import kuit2.server.common.response.BaseExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class JwtExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JwtBadRequestException.class)
    public BaseExceptionResponse handle_JwtBadRequestException(JwtBadRequestException e) {
        log.error("[handle_JwtBadRequestException]", e);
        return new BaseExceptionResponse(e.getExceptionStatus());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtUnauthorizedTokenException.class)
    public BaseExceptionResponse handle_JwtUnauthorizedException(JwtUnauthorizedTokenException e) {
        log.error("[handle_JwtUnauthorizedException]", e);
        return new BaseExceptionResponse(e.getExceptionStatus());
    }
}

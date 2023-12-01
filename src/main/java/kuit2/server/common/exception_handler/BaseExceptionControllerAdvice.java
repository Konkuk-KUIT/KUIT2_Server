package kuit2.server.common.exception_handler;

import jakarta.validation.ConstraintViolationException;
import kuit2.server.common.exception.BadRequestException;
import kuit2.server.common.exception.InternalServerErrorException;
import kuit2.server.common.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@RestControllerAdvice
public class BaseExceptionControllerAdvice {

    //유효하지 않은 url 요청이 왔을 때, 예외처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class, NoHandlerFoundException.class, TypeMismatchException.class})
    public BaseErrorResponse handle_BadRequest(Exception e) {
        log.error("[handle_BadRequest]", e);
        return new BaseErrorResponse(URL_NOT_FOUND);
    }

    // 위와 동일 (return ResponseEntity<>)
    /*
    @ExceptionHandler({BadRequestException.class, NoHandlerFoundException.class, TypeMismatchException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<BaseErrorResponse> handle_BadRequest(BadRequestException e) {
        log.error("[handle_BadRequest]", e);
        return ResponseEntity.badRequest().body(new BaseErrorResponse(e.getExceptionStatus()));
    }
     */

    //유효한 url로 들어왔을 때 이 url이 지원하지 않는 http method 로 요청을 했을 때 예외
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseErrorResponse handle_HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("[handle_HttpRequestMethodNotSupportedException]", e);
        return new BaseErrorResponse(METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseErrorResponse handle_ConstraintViolationException(ConstraintViolationException e) {
        log.error("[handle_ConstraintViolationException]", e);
        return new BaseErrorResponse(BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorException.class)
    public BaseErrorResponse handle_InternalServerError(InternalServerErrorException e) {
        log.error("[handle_InternalServerError]", e);
        return new BaseErrorResponse(e.getExceptionStatus());
    }

    //어떠한 ControllerAdvice를 거치더라도 해결 안된 Exception 처리
    //다른 ControllerAdvice에 Priority를 부여하여 다른걸 거치고 BaseExceptionControllerAdvice로 올 수 있게 처리함.
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public BaseErrorResponse handle_RuntimeException(Exception e) {
        log.error("[handle_RuntimeException]", e);
        return new BaseErrorResponse(SERVER_ERROR);
    }

}

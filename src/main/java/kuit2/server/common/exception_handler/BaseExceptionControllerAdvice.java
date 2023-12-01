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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class, NoHandlerFoundException.class, TypeMismatchException.class})
    public BaseErrorResponse handle_BadRequest(Exception e) {
        log.error("[handle_BadRequest]", e);//유효하지 않은 url에 접속했을때 예외
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
    /**어떤 예외 컨트롤러에서도 해결하지 못했을때..!*/
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public BaseErrorResponse handle_RuntimeException(Exception e) {
        log.error("[handle_RuntimeException]", e);
        return new BaseErrorResponse(SERVER_ERROR);
    }

}

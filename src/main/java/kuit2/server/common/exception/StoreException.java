package kuit2.server.common.exception;

import kuit2.server.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class StoreException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public StoreException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }



}

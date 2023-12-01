package kuit2.server.common.exception;

import kuit2.server.common.response.status.ResponseStatus;

public class OrderException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public OrderException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public OrderException(ResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }
}

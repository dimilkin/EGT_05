package com.gateway.exceptions;

public class RequestDuplicationException extends RuntimeException{

    public RequestDuplicationException(String message) {
        super(message);
    }

    public RequestDuplicationException(String message, Exception cause) {
        super(message, cause);
    }

    public RequestDuplicationException(Exception cause) {
        super(cause);
    }

    public RequestDuplicationException() {
        super();
    }
}

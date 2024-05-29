package com.gateway.exceptions;

import java.time.LocalDateTime;

public class RequestErrorMessage {

    private LocalDateTime timestamp;
    private String message;

    public RequestErrorMessage(String message) {
       this(LocalDateTime.now(), message);
    }

    public RequestErrorMessage(LocalDateTime timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

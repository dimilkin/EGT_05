package com.gateway.exceptions;

import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestDuplicationException.class)
    public ResponseEntity<RequestErrorMessage> handleResourceNotFoundException(RequestDuplicationException ex, WebRequest request) {
        RequestErrorMessage body = new RequestErrorMessage("Request has been already served");
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RequestErrorMessage> handleEntitiyNotFound(EntityNotFoundException ex, WebRequest request) {
        RequestErrorMessage body = new RequestErrorMessage(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}

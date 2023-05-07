package dev.noelopez.restdemo1.handler;

import dev.noelopez.restdemo1.exception.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    MessageSource messageSource;
//
//    public GlobalExceptionHandler(MessageSource messageSource) {
//        this.messageSource = messageSource;
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    RestErrorResponse handleException(MethodArgumentNotValidException ex) {
        String message = ex.getFieldErrors()
                .stream()
                .map(e -> " Field "+e.getField() + ". Message " + e.getDefaultMessage())
                .reduce("Errors found:", String::concat);
        return new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), message, LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    RestErrorResponse handleException(Exception ex) {
        return new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), LocalDateTime.now());
    }

}

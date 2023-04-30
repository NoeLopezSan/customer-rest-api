package dev.noelopez.restdemo1.handler;

import dev.noelopez.restdemo1.exception.RestErrorResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {

    MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    RestErrorResponse handleCustomerNotFoundException(MethodArgumentNotValidException ex, Locale locale) {
        return new RestErrorResponse(HttpStatus.BAD_REQUEST.value(),
                String.valueOf( ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(f -> messageSource.getMessage(f.getCodes()[0],null,locale)).reduce("Error(s): " , String::concat)),
                LocalDateTime.now());
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    RestErrorResponse handleException(Exception ex) {
        return new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), LocalDateTime.now());
    }
}

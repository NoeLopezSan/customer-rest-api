package dev.noelopez.restdemo1.handler;

import dev.noelopez.restdemo1.exception.EntityNotFoundException;
import dev.noelopez.restdemo1.exception.RestErrorResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
    RestErrorResponse handleException(MethodArgumentNotValidException ex, Locale locale) {
        String message = ex.getFieldErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .reduce(messageSource.getMessage("errors.found", null, locale), String::concat);
        return new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), message, LocalDateTime.now());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    RestErrorResponse handleException(EntityNotFoundException ex, Locale locale) {
        return new RestErrorResponse(HttpStatus.BAD_REQUEST.value(),
                messageSource.getMessage("entity.notFound", new Object[]{ ex.getTheClassName(), ex.getId()} , locale),
                LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    RestErrorResponse handleException(MethodArgumentTypeMismatchException ex, Locale locale) {
        return new RestErrorResponse(HttpStatus.BAD_REQUEST.value(),
                messageSource.getMessage("exception.mismatch", new Object[] {ex.getValue(),ex.getRequiredType().getSimpleName()}, locale),
                LocalDateTime.now());
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    RestErrorResponse handleException(Exception ex, Locale locale) {
        return new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), messageSource.getMessage("exception.general",null, locale), LocalDateTime.now());
    }
}

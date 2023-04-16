package dev.noelopez.restdemo1.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Customer "+id+" not found!");
    }
}

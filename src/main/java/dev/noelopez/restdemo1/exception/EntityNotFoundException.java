package dev.noelopez.restdemo1.exception;

import jakarta.validation.constraints.NotNull;

public class EntityNotFoundException extends RuntimeException {
    private Long id;
    private Class theClass;
    public EntityNotFoundException(@NotNull Long id,@NotNull Class theClass) {
        super(theClass.getName()+" "+id+" not found!");
        this.id = id;
        this.theClass = theClass;
    }

    public Long getId() {
        return id;
    }

    public String getTheClassName() {
        return theClass.getSimpleName();
    }
}

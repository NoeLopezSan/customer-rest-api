package dev.noelopez.restdemo1.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedExtensionsConstrainValidator.class)
public @interface AllowedExtensions {
    public String[] value() default {"pdf","txt","png"};

    public String message() default "File extension must be pdf, txt or png";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}

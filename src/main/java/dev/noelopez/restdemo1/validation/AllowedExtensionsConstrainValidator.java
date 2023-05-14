package dev.noelopez.restdemo1.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class AllowedExtensionsConstrainValidator implements ConstraintValidator<AllowedExtensions, String> {
    private String[] extensions;

    @Override
    public void initialize(AllowedExtensions allowedExtensions) {
        this. extensions = allowedExtensions.value();
    }

    @Override
    public boolean isValid(String fileName, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.asList(extensions).contains(fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase());
    }
}

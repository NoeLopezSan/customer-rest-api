package dev.noelopez.restdemo1.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
public record CustomerRequest(
        @NotBlank(message = "{customer.name.required}")
        @Size(min = 3, max = 20, message = "{customer.name.size}")
        @Pattern(regexp = "[a-zA-Z\\s]+", message = "{customer.name.invalid}")
        String name,
        @NotBlank(message = "{customer.email.required}")
        @Email(message = "{customer.email.invalid}")
        String email,
        @Past(message = "{customer.dob.past}") LocalDate dateOfBirth,
        @Size(max = 500, message = "{customer.info.max}")
        String info,
        boolean vip) {
}

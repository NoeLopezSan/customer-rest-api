package dev.noelopez.restdemo1.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

//public record CustomerRequest(@NotBlank(message = "Name Cannot be Blank") String name,@NotBlank(message = "Email Cannot be Blank")   String email,@Past(message = "Date is in past") LocalDate dateOfBirth) {
public record CustomerRequest(
        @NotBlank(message = "Name is required.")
        @Size(min = 3, max = 20, message = "Name must be at least 3 characters and at most 20 characters.")
        @Pattern(regexp = "[a-zA-Z\\s]+", message = "Name can only contain letters and spaces.") String name,
        @NotBlank(message = "Name is required") @Email(message = "Email is not valid.") String email,
        @Past(message = "Date of Birth must be in the past.") LocalDate dateOfBirth) {
}

package dev.noelopez.restdemo1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

//public record CustomerRequest(@NotBlank(message = "Name Cannot be Blank") String name,@NotBlank(message = "Email Cannot be Blank")   String email,@Past(message = "Date is in past") LocalDate dateOfBirth) {
public record CustomerRequest(@NotBlank String name,@NotBlank String email,@Past LocalDate dateOfBirth) {
}

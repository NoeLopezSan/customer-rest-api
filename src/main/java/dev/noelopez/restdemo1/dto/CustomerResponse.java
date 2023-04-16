package dev.noelopez.restdemo1.dto;

import java.time.LocalDate;
public record CustomerResponse(long id, String name, String email, LocalDate dateOfBirth) {

}

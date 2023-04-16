package dev.noelopez.restdemo1.dto;

import java.time.LocalDate;

public record CustomerRequest(String name, String email, LocalDate dateOfBirth) {

}

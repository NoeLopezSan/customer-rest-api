package dev.noelopez.restdemo1.dto;

import java.time.LocalDate;

public record DocumentResponse(String name, LocalDate creationDate, int size, String type) {}

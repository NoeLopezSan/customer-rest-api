package dev.noelopez.restdemo1.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record RestErrorResponse(int status, String message, @JsonFormat(pattern = "dd/MM-yyyy HH:mm:ss") LocalDateTime timestamp) {}

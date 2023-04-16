package dev.noelopez.restdemo1.exception;

import java.time.LocalDateTime;

public record RestErrorResponse(int status, String message, LocalDateTime timestamp) {}

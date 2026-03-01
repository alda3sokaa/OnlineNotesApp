package com.app.backend.dto;

import java.time.Instant;

public record ApiError(String message, Instant timestamp) {
}
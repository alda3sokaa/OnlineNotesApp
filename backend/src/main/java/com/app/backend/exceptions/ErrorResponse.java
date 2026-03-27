package com.app.backend.exceptions;

import java.time.LocalDateTime;

public class ErrorResponse {

    private String message;
    private int status;
    private String error;
    private LocalDateTime timestamp;

    public ErrorResponse(String message, int status, String error) {
        this.message = message;
        this.status = status;
        this.error = error;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

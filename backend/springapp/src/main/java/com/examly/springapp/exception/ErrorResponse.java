package com.examly.springapp.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    private int status;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private List<String> errors;

    public ErrorResponse() {}

    public ErrorResponse(int status, String message, String path, LocalDateTime timestamp, List<String> errors) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
        this.errors = errors;
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
}

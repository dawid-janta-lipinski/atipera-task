package com.example.atiperatask.exceptions;

public class HttpRequestException extends RuntimeException {
    public HttpRequestException(String message) {
        super(message);
    }

    public HttpRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestException(Throwable cause) {
        super(cause);
    }
}

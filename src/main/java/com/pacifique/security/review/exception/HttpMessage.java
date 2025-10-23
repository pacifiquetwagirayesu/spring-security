package com.pacifique.security.review.exception;

import java.time.LocalDateTime;

public record HttpMessage(String message, int status, LocalDateTime timestamp, String path) {

    public HttpMessage(String message, int status, String path) {
        this(message, status, LocalDateTime.now(), path);
    }
}

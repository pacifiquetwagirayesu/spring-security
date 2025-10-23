package com.pacifique.security.review.exception;

public class InvalidToken extends RuntimeException {
    public InvalidToken(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidToken(String message) {
        super(message);
    }
}

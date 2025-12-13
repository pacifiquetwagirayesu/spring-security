package com.pacifique.security.review.exception;

public class ProductNotFound extends NotFoundException {
    public ProductNotFound(String message) {
        super(message);
    }

    public ProductNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}

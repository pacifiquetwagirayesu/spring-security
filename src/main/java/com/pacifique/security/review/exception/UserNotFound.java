package com.pacifique.security.review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFound extends NotFoundException {
    public UserNotFound(String username) {
        super(username);
    }

    public UserNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}

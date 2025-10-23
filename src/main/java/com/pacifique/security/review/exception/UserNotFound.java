package com.pacifique.security.review.exception;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String username) {
        super(username);
    }
}

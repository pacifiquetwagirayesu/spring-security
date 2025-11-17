package com.pacifique.security.review.dto;

public record UserLoginRequest(
        String username,
        String password) { }

package com.pacifique.security.review.dto;

public record UserRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String role
) {

    public UserRequest {
        firstName = firstName.strip();
        lastName = lastName.strip();
        email = email.strip();
        password = password.strip();
        role = role.strip().toUpperCase();

        if (role.isBlank()) {
            role = "USER";
        }

        if (firstName.isBlank()) throw new IllegalArgumentException("First name is required");
        if (lastName.isBlank()) throw new IllegalArgumentException("Last name is required");
        if (email.isBlank()) throw new IllegalArgumentException("Email is required");
        if (password.isBlank()) throw new IllegalArgumentException("Password is required");

    }
}

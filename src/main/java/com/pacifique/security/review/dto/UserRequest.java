package com.pacifique.security.review.dto;

import com.pacifique.security.review.model.Role;

import java.util.List;

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

        boolean contains = List.of(Role.ADMIN.name(), Role.MANAGER.name(), Role.USER.name()).contains(role);

        if (role.isBlank()) {
            role = "USER";
        }

        if (!contains)
            throw new IllegalArgumentException("Invalid role, eg: admin,manager,user");

        if (firstName.isBlank()) throw new IllegalArgumentException("First name is required");
        if (lastName.isBlank()) throw new IllegalArgumentException("Last name is required");
        if (email.isBlank()) throw new IllegalArgumentException("Email is required");
        if (password.isBlank()) throw new IllegalArgumentException("Password is required");

    }
}

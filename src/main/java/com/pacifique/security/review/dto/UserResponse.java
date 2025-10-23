package com.pacifique.security.review.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
        long id,
        String firstName,
        String lastName,
        String email,
        @JsonIgnore String password,
        String role,
        Set<String> permissions,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

package com.pacifique.security.review.dto;

public record ProductRequest(
        String name,
        String description
) {
    public ProductRequest {
        name = name.strip();
        description = description.strip();
    }
}

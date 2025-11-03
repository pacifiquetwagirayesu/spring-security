package com.pacifique.security.review.dto;

import java.util.Map;

public record UserLoginResponse(
        long id,
        String email,
        String firstName,
        String lastName,
        Map<String, String> tokens
) {
}

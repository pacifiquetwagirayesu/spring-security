package com.pacifique.security.review.dto;

import java.time.LocalDateTime;

public record ProductResponse(Long id,
                              String name,
                              String description,
                              UserResponse owner,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt) {

}

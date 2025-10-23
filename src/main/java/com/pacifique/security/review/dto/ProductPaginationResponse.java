package com.pacifique.security.review.dto;

import java.util.List;

public record ProductPaginationResponse(
        long totalElements,
        long totalPage,
        long page,
        List<ProductResponse> products
) {
}

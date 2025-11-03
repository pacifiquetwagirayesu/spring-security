package com.pacifique.security.review.service;

import com.pacifique.security.review.dto.ProductPaginationResponse;
import com.pacifique.security.review.dto.ProductRequest;
import com.pacifique.security.review.dto.ProductResponse;

import java.util.List;
import java.util.Map;

public interface IProductService {
    Iterable<ProductResponse> getProducts(int page, int size);

    ProductResponse getProductById(Long id);

    ProductPaginationResponse getMyProducts(int page, int size);

    ProductResponse addProduct(ProductRequest req);

    Iterable<ProductResponse> addProducts(List<ProductRequest> req);

    Map<String, String> deleteProduct(long id);

}

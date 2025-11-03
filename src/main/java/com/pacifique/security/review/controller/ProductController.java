package com.pacifique.security.review.controller;

import com.pacifique.security.review.dto.ProductPaginationResponse;
import com.pacifique.security.review.dto.ProductRequest;
import com.pacifique.security.review.dto.ProductResponse;
import com.pacifique.security.review.service.IProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@RestController
@Tag(name = "Product Controller", description = "Product related actions")
public class ProductController {
    private final IProductService productService;

    @GetMapping
    public Iterable<ProductResponse> getProducts(@RequestParam int page, @RequestParam int size) {
        return productService.getProducts(page, size);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/me")
    public ProductPaginationResponse getMyProducts(@RequestParam int page, @RequestParam int size) {
        return productService.getMyProducts(page, size);
    }

    @PostMapping
    public ProductResponse addProduct(@RequestBody ProductRequest req) {
        return productService.addProduct(req);
    }

    @PostMapping("/all")
    public Iterable<ProductResponse> addProduct(@RequestBody List<ProductRequest> req) {
        return productService.addProducts(req);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }
}

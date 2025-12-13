package com.pacifique.security.review.controller;

import com.pacifique.security.review.dto.ProductPaginationResponse;
import com.pacifique.security.review.dto.ProductRequest;
import com.pacifique.security.review.dto.ProductResponse;
import com.pacifique.security.review.services.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @Operation(tags = "Get Action", summary = "Product list request")
    public PagedModel<ProductResponse> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return productService.getProducts(page, size);
    }

    @GetMapping("/{id}")
    @Operation(tags = "Get Action", summary = "Get product by id")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/my-products")
    @Operation(tags = "Get Action", summary = "User logged in products request")
    public ProductPaginationResponse getMyProducts(@RequestParam int page, @RequestParam int size) {
        return productService.getMyProducts(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = "Post Action", summary = "Add product request")
    public ProductResponse addProduct(@RequestBody ProductRequest req) {
        return productService.addProduct(req);
    }

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = "Post Action", summary = "Add list product request")
    public Iterable<ProductResponse> addProduct(@RequestBody List<ProductRequest> req) {
        return productService.addProducts(req);
    }

    @DeleteMapping("/{id}")
    @Operation(tags = "Delete Action", summary = "Delete product by Id request")
    public Map<String, String> deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }
}

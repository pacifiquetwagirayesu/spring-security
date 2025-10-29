package com.pacifique.security.review.service;

import com.pacifique.security.review.dto.ProductPaginationResponse;
import com.pacifique.security.review.dto.ProductRequest;
import com.pacifique.security.review.dto.ProductResponse;
import com.pacifique.security.review.exception.UserNotFound;
import com.pacifique.security.review.model.Product;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.repository.IProductRepository;
import com.pacifique.security.review.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.pacifique.security.review.utils.Utility.convertProductResponse;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final IUserRepository userRepository;

    @Override
    public Iterable<ProductResponse> getProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "name"));
        Page<Product> productPage = productRepository.findAll(pageRequest);
        List<Product> all = productPage.getContent();
        return all.stream().map(product -> convertProductResponse().apply(product)).toList();
    }

    @Override
//    @PostFilter("filterObject.owner().email == authentication.principal.username")
    public ProductPaginationResponse getMyProducts(int page, int size) {
        Page<Product> productPage = productRepository.findAllByOwner_Email(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "name")));
        Stream<Product> productStream = productPage.get();
        long totalElements = productPage.getTotalElements();
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> list = productStream.map(product -> convertProductResponse().apply(product)).toList();
        return new ProductPaginationResponse(totalElements, totalPages, Math.addExact(page, 1), list);
    }

    @Override
    @PreAuthorize("hasPermission(#id,'product','delete,write')")
    public Map<String, String> deleteProduct(long id) {
        productRepository.deleteById(id);
        return Map.of("message", "Product with id " + id + " deleted");
    }

    @Override
    public Iterable<ProductResponse> addProducts(List<ProductRequest> req) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UserNotFound("user not found"));

        List<Product> productList = req.stream().map(r -> Product.builder()
                .owner(user)
                .name(r.name())
                .description(r.description())
                .build()
        ).toList();

        log.info("Adding products loaded: {}", productList);
        List<Product> products = productRepository.saveAllAndFlush(productList);
        return products.stream().map(product -> convertProductResponse().apply(product)).toList();
    }

    @Override
    public ProductResponse addProduct(ProductRequest req) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("User not found"));

        var product = Product.builder()
                .name(req.name())
                .description(req.description())
                .owner(user)
                .createdAt(LocalDateTime.now())
                .build();

        Product savedProduct = productRepository.save(product);
        return convertProductResponse().apply(savedProduct);


    }
}

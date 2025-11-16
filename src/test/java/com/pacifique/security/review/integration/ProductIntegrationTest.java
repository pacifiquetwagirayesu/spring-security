package com.pacifique.security.review.integration;

import com.pacifique.security.review.ConfigDatabase;
import com.pacifique.security.review.dto.ProductResponse;
import com.pacifique.security.review.model.Product;
import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.repository.IProductRepository;
import com.pacifique.security.review.repository.IUserRepository;
import com.pacifique.security.review.security.JwtAuthenticationFilter;
import com.pacifique.security.review.security.SuperAdminAuthFilter;
import com.pacifique.security.review.services.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest extends ConfigDatabase {
    private static RestTemplate restTemplate;
    @LocalServerPort
    private int port;
    private String baseUrl = "http://localhost:";
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IUserRepository userRepository;

    @BeforeAll
    static void beforeAll() {
        restTemplate = new RestTemplate();
    }

    @AfterEach
    void afterAll() {
        productRepository.deleteAll();
        userRepository.deleteAll();
    }


    @BeforeEach
    void beforeEach() {
        User user = User.builder()
                .password("password")
                .role(Role.USER.name())
                .permissions(Role.USER.getPermissions())
                .lastName("l")
                .firstName("f")
                .email("user@gmail.com")
                .createdAt(LocalDateTime.now()).build();

        User savedUser = userRepository.save(user);

        Product product = Product.builder()
                .description("desc")
                .name("name")
                .owner(savedUser)
                .build();
        productRepository.save(product);

        baseUrl = baseUrl + port + "/api/v1/products";

    }


    @Test
    @DisplayName("Integration Test Get Product by ID")
    void getProductByIdTest() {
        Map<String, Long> urlVariables = Map.of("id", 1L);
        ProductResponse response = restTemplate.getForObject(baseUrl + "/{id}", ProductResponse.class, urlVariables);

        assertNotNull(response);
        assertEquals(1,response.id());
    }

}

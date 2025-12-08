package com.pacifique.security.review.integration;

import com.pacifique.security.review.config.ConfigDatabase;
import com.pacifique.security.review.dto.ProductPaginationResponse;
import com.pacifique.security.review.dto.ProductRequest;
import com.pacifique.security.review.dto.ProductResponse;
import com.pacifique.security.review.dto.UserLoginRequest;
import com.pacifique.security.review.dto.UserLoginResponse;
import com.pacifique.security.review.model.Product;
import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.repository.IProductRepository;
import com.pacifique.security.review.repository.IUserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
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
    @Autowired
    private PasswordEncoder passwordEncoder;
    private User savedUser;

    @BeforeAll
    static void beforeAll() {
        restTemplate = new RestTemplate();
    }


    @BeforeEach
    void beforeEach() {
        productRepository.deleteAll();
        userRepository.deleteAll();

        var user = User.builder()
                .password(passwordEncoder.encode("password"))
                .role(Role.USER.name())
                .permissions(Role.USER.getPermissions())
                .lastName("l")
                .firstName("f")
                .email("user@gmail.com")
                .createdAt(LocalDateTime.now()).build();

        savedUser = userRepository.save(user);

        var product = Product.builder()
                .description("desc")
                .name("name")
                .owner(savedUser)
                .build();
        productRepository.save(product);

        baseUrl = baseUrl + port + "/api/v1";

    }


    @Test
    @DisplayName("Integration Test Get Product by ID")
    void getProductByIdTest() {
        Product product = productRepository.save(Product.builder().owner(savedUser).description("desc2").name("name2").build());
        ProductResponse response = restTemplate.getForObject(baseUrl + "/products/" + product.getId(), ProductResponse.class);
        assertNotNull(response);
        assertEquals(product.getId(), response.id());
    }

    @Test
    @DisplayName("Integration Test For Get All products Endpoint")
    void getAllProductsTest() {
        String url = UriComponentsBuilder.fromUriString(baseUrl + "/products")
                .queryParam("page", 0)
                .queryParam("size", 1)
                .build()
                .toUri().toString();

        ResponseEntity<List<ProductResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("Integration Test for Get My Products Endpoint")
    void getMyProductsTest() {
        String accessToken = authenticateAndGetToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ProductPaginationResponse> response = restTemplate.exchange(
                baseUrl + "/products/me?page=0&size=1",
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<>() {
                });

        System.out.println("response = " + response);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().products().size());
    }


    @Test
    @DisplayName("Integration add product Test")
    void addProductTest() {
        String accessToken = authenticateAndGetToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductRequest> entity = new HttpEntity<>(
                new ProductRequest("name2", "desc2"),
                headers);

        ResponseEntity<ProductResponse> response = restTemplate.postForEntity(
                baseUrl + "/products", entity, ProductResponse.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("desc2", response.getBody().description());
        assertEquals("name2", response.getBody().name());
        assertEquals("user@gmail.com", response.getBody().owner().email());

    }


    @Test
    @DisplayName("Integration add List product Test")
    void addListProductTest() {
        String accessToken = authenticateAndGetToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<ProductRequest>> entity = new HttpEntity<>(
                List.of(new ProductRequest("name2", "desc2"),
                        new ProductRequest("name3", "desc3")),
                headers);

        ResponseEntity<List<ProductResponse>> response = restTemplate.exchange(
                baseUrl + "/products/all", HttpMethod.POST, entity, new ParameterizedTypeReference<>() {
                });

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("user@gmail.com", response.getBody().get(1).owner().email());
    }

    @Test
    @DisplayName("Integration delete Product Test")
    void deleteProductTest() {
        Product product = productRepository.save(Product.builder().owner(savedUser).description("desc5").name("name5").build());
        String accessToken = authenticateAndGetToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(baseUrl + "/products/" + product.getId(),
                HttpMethod.DELETE, entity,
                new ParameterizedTypeReference<>() {
                });

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Product with id " + product.getId() + " deleted", response.getBody().get("message"));
    }


    private String authenticateAndGetToken() {
        String url = baseUrl + "/auth/login";

        UserLoginRequest loginRequest = new UserLoginRequest("user@gmail.com", "password");
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<UserLoginResponse> response = restTemplate.postForEntity(url, entity, UserLoginResponse.class);

        assertNotNull(response);
        assertNotNull(response.getBody());

        return response.getBody().tokens().get("accessToken");
    }
}

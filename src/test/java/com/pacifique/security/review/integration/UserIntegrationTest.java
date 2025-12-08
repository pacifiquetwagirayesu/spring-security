package com.pacifique.security.review.integration;

import com.pacifique.security.review.config.ConfigDatabase;
import com.pacifique.security.review.dto.UserLoginRequest;
import com.pacifique.security.review.dto.UserLoginResponse;
import com.pacifique.security.review.dto.UserRequest;
import com.pacifique.security.review.dto.UserResponse;
import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.repository.IProductRepository;
import com.pacifique.security.review.repository.IUserRepository;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest extends ConfigDatabase {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @LocalServerPort
    private int port;
    private String baseUrl;
    private User user;



    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        userRepository.deleteAll();
        baseUrl = "http://localhost:" + port + "/api/v1";

        user = userRepository.save(User.builder()
                .firstName("f")
                .lastName("l")
                .password(passwordEncoder.encode("password"))
                .email("user@gmail.com")
                .permissions(Role.ADMIN.getPermissions())
                .role(Role.ADMIN.name())
                .createdAt(LocalDateTime.now())
                .build());
    }


    @Test
    @DisplayName("Integration Test Integration get List users")
    void shouldGetAllUsersTest() {
        String accessToken = authenticateAndGetToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<User> entity = new HttpEntity<>(headers);
        ResponseEntity<List<UserResponse>> response = restTemplate.exchange(
                baseUrl + "/users", HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                });

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(user.getEmail(), response.getBody().get(0).email());
    }


    @Test
    @DisplayName("Integration Test Creating a User")
    void shouldCreateUserTest() {
        UserRequest userRequest = new UserRequest(
                "firstName",
                "lastName",
                "user12@gmail.com",
                "password",
                "user");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserRequest> entity = new HttpEntity<>(userRequest, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/users", entity, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("successfully created", response.getBody());

    }


    @Test
    @DisplayName("Integration Test updating User role")
    void shouldUpdateUserRoleTest() {
        String accessToken = authenticateAndGetToken();
        User user = userRepository.save(User.builder()
                .firstName("f")
                .lastName("l")
                .password(passwordEncoder.encode("password"))
                .email("user13@gmail.com")
                .permissions(Role.USER.getPermissions())
                .role(Role.USER.name())
                .createdAt(LocalDateTime.now())
                .build());

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        String url = baseUrl+ "/users/"+user.getId()+"?role=manager";


        HttpEntity<Void> entity = new HttpEntity<>(headers);

        restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                entity,
                new ParameterizedTypeReference<Map<String, String>>() {});

        User user1 = userRepository.findById(user.getId()).get();

        assertEquals(Role.MANAGER.name(), user1.getRole());
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

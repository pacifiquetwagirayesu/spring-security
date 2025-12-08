package com.pacifique.security.review.integration;

import com.pacifique.security.review.config.ConfigDatabase;
import com.pacifique.security.review.dto.UserLoginRequest;
import com.pacifique.security.review.dto.UserLoginResponse;
import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthIntegrationTest extends ConfigDatabase {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @LocalServerPort
    private int port;
    private String baseUrl = "http://localhost";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        userRepository.save(User.builder()
                .role(Role.ADMIN.name())
                .firstName("admin")
                .lastName("user")
                .permissions(Role.ADMIN.getPermissions())
                .email("admin@example.com")
                .password(passwordEncoder.encode("password"))
                .build());
        baseUrl = "http://localhost:" + port + "/api/v1/auth";
    }

    @Test
    @DisplayName("Integration Test for User Login")
    void loginUserTest() {
        var response = getUserLoginResponseResponse();
        assertNotNull(response);
        assertNotNull(response.tokens().get("accessToken"));

    }

    @Test
    @DisplayName("Integration Test Get User login info")
    void userLoginInfoTest() {
        var response = getUserLoginResponseResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(response.tokens().get("accessToken"));
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        var loggedInUserRes = restTemplate.exchange(baseUrl + "/me", HttpMethod.GET, entity, new ParameterizedTypeReference<UserLoginResponse>() {
        });
        assertNotNull(loggedInUserRes);
        assertNotNull(loggedInUserRes.getBody());
        assertEquals(HttpStatus.OK, loggedInUserRes.getStatusCode());
        assertEquals("admin", loggedInUserRes.getBody().firstName());

    }


    @Test
    @DisplayName("Integration Test Logout a user")
    void logoutUserTest() {
       var response = restTemplate.exchange(baseUrl + "/logout", HttpMethod.POST,null, new  ParameterizedTypeReference<Void>() {});
       assertNotNull(response);
       assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("response = " + response);

    }

    private UserLoginResponse getUserLoginResponseResponse() {
        var request = new UserLoginRequest("admin@example.com", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserLoginRequest> entity = new HttpEntity<>(request, headers);

        var response = restTemplate.exchange(baseUrl + "/login", HttpMethod.POST, entity, new ParameterizedTypeReference<UserLoginResponse>() {
        });
        assertEquals(HttpStatus.OK, response.getStatusCode());
        return response.getBody();
    }


}

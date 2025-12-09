package com.pacifique.security.review.integration;

import com.pacifique.security.review.config.ConfigDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminIntegrationTest extends ConfigDatabase {
    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;
    private String baseUrl = "http://localhost:";
    @Value("${security.jwt.super-admin}")
    private String jwtSuperAdmin;

    @BeforeEach
    void setUp() {
        baseUrl = baseUrl + port + "/api/v1/admin";
    }

    @Test
    @DisplayName("Integration Test for admin")
    void adminIntegrationTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtSuperAdmin);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        var res = restTemplate.exchange(baseUrl, HttpMethod.GET,
                entity, new ParameterizedTypeReference<Map<String, String>>() {
                });
        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertNotNull(res.getBody());
        assertEquals("admin@gmail.com", res.getBody().get("user"));

    }


}

package com.pacifique.security.review;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class ConfigDatabase {

    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:10");

    static {
        database.start();
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
        registry.add("spring.datasource.driver-class-name", database::getDriverClassName);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("security.jwt.secret-key", () -> "c3ByaW5zZWN1cml0eXRlc3Q9c3ByaW5zZWN1cml0eXRlc3Q9c3ByaW5zZWN1cml0eXRlc3Q=");
        registry.add("security.jwt.expiration", () -> 30000L);
        registry.add("security.jwt.refresh-token.expiration", () -> 60000L);
        registry.add("resend.api-key", () -> "api-key");
        registry.add("resend.from", () -> "resend@gmail.com");
        registry.add("security.jwt.super-admin", () -> "super-admin");
    }


}

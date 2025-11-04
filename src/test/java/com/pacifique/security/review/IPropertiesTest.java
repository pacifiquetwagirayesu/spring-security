package com.pacifique.security.review;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public interface IPropertiesTest {


    @DynamicPropertySource
    static void dynamicTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "url");
        registry.add("spring.datasource.username", () -> "root");
        registry.add("spring.datasource.password", () -> "rootpwd");
        registry.add("security.jwt.secret-key", () -> "secret-key");
        registry.add("security.jwt.expiration", () -> 30000L);
        registry.add("security.jwt.refresh-token.expiration", () -> 60000L);
        registry.add("resend.api-key", () -> "api-key");
        registry.add("resend.from", () -> "resend@gmail.com");
        registry.add("security.jwt.super-admin", () -> "super-admin");
    }
}

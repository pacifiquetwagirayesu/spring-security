package com.pacifique.security.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
@Slf4j
@Tag(name = "Admin Controller", description = "Operation related to admin")
public class AdminController {

    @Operation(
            operationId = "admin",
            summary = "Admin Operation"
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> admin(Authentication authentication) {
        return Map.of("user",authentication.getName());
    }
}

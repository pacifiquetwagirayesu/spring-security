package com.pacifique.security.review.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
@Tag(name = "Admin Controller",description = "Operation related to admin")
public class AdminController {

    @GetMapping
    public String index(Authentication authentication) {
        return "Admin Controller " + authentication.getName();
    }
}

package com.pacifique.security.review.controller;

import com.pacifique.security.review.dto.UserLoginRequest;
import com.pacifique.security.review.dto.UserLoginResponse;
import com.pacifique.security.review.dto.UserResponse;
import com.pacifique.security.review.services.IAuthUserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Operation related to authentication and authorization")
public class AuthController {
    private final IAuthUserService authUserService;

    @PostMapping("/login")
    @Operation(tags = "Post Action", summary = "Log in user request")
    public UserLoginResponse logInUser(@RequestBody UserLoginRequest req) {
        return authUserService.logInUser(req);
    }


    @GetMapping("/me")
    @Operation(tags = "Get Action", summary = "Get logged in user info")
    public UserResponse loggedInUser() {
        return authUserService.loggedInUser();
    }

    @PostMapping("/logout")
    @Operation(tags = "Post Action")
    @Hidden
    public void logoutUser(HttpServletResponse response) throws IOException {
        SecurityContextHolder.clearContext();
        response.sendRedirect("/swagger-ui/index.html");
        log.debug("Logout requested");
    }

}

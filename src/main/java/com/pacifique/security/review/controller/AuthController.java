package com.pacifique.security.review.controller;

import com.pacifique.security.review.dto.UserLoginRequest;
import com.pacifique.security.review.dto.UserLoginResponse;
import com.pacifique.security.review.dto.UserResponse;
import com.pacifique.security.review.service.IAuthUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Operation related to Authentication and Authorization")
public class AuthController {
    private final IAuthUserService authUserService;

    @PostMapping("/login")
    public UserLoginResponse logInUser(@RequestBody UserLoginRequest req) {
        return authUserService.logInUser(req);
    }


    @GetMapping("/me")
    public UserResponse loggedInUser() {
        return authUserService.loggedInUser();
    }

    @PostMapping("/logout")
    public void logoutUser() {
        log.debug("Logout request");
    }

}

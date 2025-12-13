package com.pacifique.security.review.controller;

import com.pacifique.security.review.dto.UserRequest;
import com.pacifique.security.review.dto.UserResponse;
import com.pacifique.security.review.services.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "User Controller", description = "Operations related to user")
public class UserController {
    private final IUserService userService;

    /**
     *
     * @return List of UserResponse
     */
    @Operation(summary = "List users request", tags = "Get Action", description = "Returns  list of users")
    @ApiResponse(
            responseCode = "200", description = "Users found",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @GetMapping
    public Iterable<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = "Post Action", summary = "Register a user request")
    public String createUser(@RequestBody UserRequest req) {
        return userService.createUser(req);
    }

    @PatchMapping("/{id}")
    @Operation(tags = "Update Action", summary = "Update user role request")
    public Map<String, String> updateUserRole(@PathVariable long id, @RequestParam @Parameter(
            description = "User role. Available values: user, manager",
            example = "manager"
    ) String role) {
        return userService.updateUserRole(id, role);
    }

    @DeleteMapping("/{id}")
    @Operation(tags = "Delete Action", summary = "Delete user by id request")
    public String deleteUserById(@PathVariable long id) {
        return userService.deleteUserById(id);
    }


}

package com.pacifique.security.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacifique.security.review.dto.UserRequest;
import com.pacifique.security.review.dto.UserResponse;
import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.security.JwtAuthenticationFilter;
import com.pacifique.security.review.security.SuperAdminAuthFilter;
import com.pacifique.security.review.services.UserService;
import com.pacifique.security.review.utils.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private SuperAdminAuthFilter superAdminAuthFilter;
    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("email@gmail.com")
                .password("password")
                .firstName("firstName")
                .lastName("lastName")
                .role("USER")
                .permissions(Role.USER.getPermissions())
                .build();
    }


    @Test
    @DisplayName("Test Getting List User Controller")
    void shouldReturnListUser() throws Exception {
        UserResponse userResponse = Utility.convertUserResponse().apply(user);
        when(userService.getAllUsers()).thenReturn(List.of(userResponse));

        this.mockMvc.perform(get("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1));
    }


    @Test
    @DisplayName("Test Creating A User Controller")
    void shouldCreateAUser() throws Exception {
        UserRequest userRequest = new UserRequest("firstName", "lastName", "user@gmail.com", "password", "user");
        when(userService.createUser(any(UserRequest.class))).thenReturn("success");
        this.mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("success"));
    }


    @Test
    @DisplayName("Test Updating A User Role Controller")
    void shouldUpdateAUserRole() throws Exception {
        when(userService.updateUserRole(anyLong(), anyString())).thenReturn(Map.of("message", "success"));
        this.mockMvc.perform(patch("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("role", "USER")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("success"));
    }

    @Test
    @DisplayName("Test Deleting  a User controller")
    void shouldDeleteAUser() throws Exception {
        when(userService.deleteUserById(anyLong())).thenReturn("success");
        this.mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("success"));
    }

}

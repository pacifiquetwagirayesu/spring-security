package com.pacifique.security.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacifique.security.review.dto.UserLoginRequest;
import com.pacifique.security.review.dto.UserLoginResponse;
import com.pacifique.security.review.dto.UserResponse;
import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.model.Token;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.repository.IUserRepository;
import com.pacifique.security.review.security.AuthUser;
import com.pacifique.security.review.security.JwtAuthenticationFilter;
import com.pacifique.security.review.security.SuperAdminAuthFilter;
import com.pacifique.security.review.services.IAuthUserService;
import com.pacifique.security.review.utils.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {JwtAuthenticationFilter.class, SuperAdminAuthFilter.class}
        ))
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IUserRepository userRepository;
    @MockitoBean
    private IAuthUserService authUserService;
    @MockitoBean
    private PasswordEncoder passwordEncoder;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .firstName("f")
                .lastName("l")
                .email("user@example.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.ADMIN.name())
                .permissions(Role.ADMIN.getPermissions())
                .build();
    }

    @Test
    @DisplayName("Testing Login User Controller")
    void loginUserTest() throws Exception {
        var request = new UserLoginRequest("user@example.com", "password");
        AuthUser authUser = AuthUser.getUser(user);
        Token token = Token.builder().token("accessToken").refreshToken("refreshToken").build();
        UserLoginResponse userLoginResponse = Utility.loginUserResponse(authUser, token);
        when(authUserService.logInUser(request)).thenReturn(userLoginResponse);

        this.mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.tokens.size()").value(2))
                .andExpect(jsonPath("$.tokens.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("user@example.com"));
    }

    @Test
    @DisplayName("Testing Getting logged in User info")
    void testLoggedInUserInfo() throws Exception{
        UserResponse response = Utility.convertUserResponse().apply(user);
        when(authUserService.loggedInUser()).thenReturn(response);

        this.mockMvc.perform(get("/api/v1/auth/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@example.com"));
    }
}

package com.pacifique.security.review.service;

import com.pacifique.security.review.dto.UserLoginRequest;
import com.pacifique.security.review.dto.UserLoginResponse;
import com.pacifique.security.review.dto.UserResponse;
import com.pacifique.security.review.exception.InvalidToken;
import com.pacifique.security.review.exception.UserNotFound;
import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.model.Token;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.repository.ITokenRepository;
import com.pacifique.security.review.repository.IUserRepository;
import com.pacifique.security.review.security.AuthUser;
import com.pacifique.security.review.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthUserServiceTest {
    @Mock
    private ITokenRepository tokenRepository;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private IJwtService jwtService;
    @Mock
    private AuthUser authUser;
    private AuthUserService authUserService;

    private UserLoginRequest userLoginRequest;
    private Token userToken;
    private User user;

    @BeforeEach
    void setUp() {
        tokenRepository = mock(ITokenRepository.class);
        userRepository = mock(IUserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(IJwtService.class);
        authUserService = new AuthUserService(
                tokenRepository, userRepository, passwordEncoder, jwtService, userDetailsService);
        when(passwordEncoder.encode(anyString())).thenReturn("password");

        authUser = AuthUser.builder().password(passwordEncoder.encode("pass")).build();
        userToken = Token.builder().refreshToken("refreshToken").token("token").build();
        userLoginRequest = new UserLoginRequest("username@mail", "password");
        user = User.builder().id(1L).email("username@mail").build();

    }

    @Test
    void userInvalidTokenTest() {
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(authUser);
        assertThrows(InvalidToken.class, () ->
                        authUserService.logInUser(userLoginRequest),
                "Invalid token"
        );
    }

    @Test
    void userInvalidCredentialsTest() {
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(authUser);
        when(tokenRepository.findByUserId(anyLong())).thenReturn(Optional.of(userToken));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        assertThrows(UserNotFound.class, () -> authUserService.logInUser(userLoginRequest)
                , "Please check your username and password");

    }

    @Test
    void userValidCredentialsTest() {
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(authUser);
        when(tokenRepository.findByUserId(anyLong())).thenReturn(Optional.of(userToken));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtService.isTokenValid(anyString(), any(AuthUser.class))).thenReturn(Boolean.TRUE);

        UserLoginResponse res = authUserService.logInUser(userLoginRequest);

        assertNotNull(res);
        assertEquals("token", res.tokens().get("accessToken"));
    }


    @Test
    void userValidCredentialsInvalidTokenTest() {
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(authUser);
        when(tokenRepository.findByUserId(anyLong())).thenReturn(Optional.of(userToken));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtService.isTokenValid(anyString(), any(AuthUser.class))).thenReturn(false);
        doNothing().when(tokenRepository).delete(any(Token.class));
        when(jwtService.generateToken(any(AuthUser.class))).thenReturn("token");
        when(jwtService.generateRefreshToken(any(AuthUser.class))).thenReturn("refreshToken");

        doAnswer((Answer<Token>) invocation -> {
            System.out.println("invocation = " + invocation.getArgument(0));
            userToken.setRefreshToken("new RefreshToken");
            userToken.setToken("new Token");
            return userToken;
        }).when(tokenRepository).save(any(Token.class));

        UserLoginResponse res = authUserService.logInUser(userLoginRequest);

        verify(tokenRepository).delete(any(Token.class));
        assertEquals("new Token", res.tokens().get("accessToken"));

    }


    @Test
    void loggedInUserTest() {
        authenticateUser();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        UserResponse res = authUserService.loggedInUser();
        assertNotNull(res);
        assertEquals("username@mail", res.email());

    }

    @Test
    void loggedInUserNotFoundTest() {
        authenticateUser();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(UserNotFound.class, () -> authUserService.loggedInUser(), "User not found");

    }


    private void authenticateUser() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(authUser.getUsername(), null);

        SecurityContext securityContext = mock(SecurityContext.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        authenticationManager.authenticate(authentication);
        SecurityContextHolder.setContext(securityContext);

        verify(authenticationManager).authenticate(authentication);

    }

}

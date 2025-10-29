package com.pacifique.security.review.service;

import com.pacifique.security.review.dto.UserRequest;
import com.pacifique.security.review.dto.UserResponse;
import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private IUserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Logger logger;
    private UserRequest userRequest;
    private  User user;


    @BeforeEach
    void setUp() {
        logger = mock(Logger.class);
        userRequest = new UserRequest("user",
                "user2", "user@mail", "userpass", "user");
        user = User.builder()
                .id(1L)
                .role(userRequest.role().toUpperCase())
                .email(userRequest.email())
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .password(passwordEncoder.encode(userRequest.password()))
                .permissions(Role.valueOf(userRequest.role().toUpperCase()).getPermissions())
                .build();
    }

    @Test
    void createUserTest() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        doAnswer((Answer<String>) invocation -> Base64.getEncoder().encodeToString("encoded".getBytes())
        ).when(passwordEncoder).encode(anyString());
        doNothing().when(emailService).sendEmail(any(User.class), anyString());

        userService.createUser(userRequest);

        verify(emailService, times(1)).sendEmail(any(User.class), anyString());
    }

    @Test
    void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(List.of());
        Iterable<UserResponse> allUsers = userService.getAllUsers();
        assertFalse(allUsers.iterator().hasNext());
    }


    @Test
    void updateUserWithSameRoleTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        String res = userService.updateUserRole(1L, "user");
        assertNull(res);
    }


    @Test
    void updateUserWithDifferentRoleTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        doNothing().when(emailService).sendEmail(any(User.class), anyString());
        String actualRes = userService.updateUserRole(1L, "admin");

        assertEquals( "{\"success\" :\"user updated\"}", actualRes);
        verify(emailService, times(1)).sendEmail(any(User.class), anyString());
    }

    @Test
    void deleteUserById() {
        String actualResponse = userService.deleteUserById(1L);
        verify(userRepository, times(1)).deleteById(1L);
        assertEquals("deleted", actualResponse);
    }


}

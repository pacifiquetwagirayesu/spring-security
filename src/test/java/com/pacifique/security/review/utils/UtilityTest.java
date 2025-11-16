package com.pacifique.security.review.utils;

import com.pacifique.security.review.security.AuthUser;
import com.pacifique.security.review.services.IJwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UtilityTest {

    private IJwtService jwtService;
    private AuthUser authUser;
    @BeforeEach
    void setUp() {
         jwtService = mock(IJwtService.class);
         authUser = mock(AuthUser.class);
    }


    @Test
    void constructorTest() {
        Exception assertThrows = assertThrows(Exception.class, Utility::new);
        assertNotNull(assertThrows);
        assertEquals("Invalid action, Utility class", assertThrows.getMessage());

    }

    @Test
    void testInvalidTokenTestReturnsFalse() {
        when(jwtService.isTokenValid(anyString(), any())).thenReturn(false);
        boolean valid = Utility.validTokeHandler("token", jwtService, authUser);
        assertFalse(valid);
    }


    @Test
    void testValidTokenTestReturnsTrue() {
        when(jwtService.isTokenValid(anyString(), any())).thenReturn(true);
        boolean valid = Utility.validTokeHandler("token", jwtService, authUser);
        assertTrue(valid);
    }

    @Test
    void testInvalidTokenTestReturnsException() {
        when(jwtService.isTokenValid(anyString(), any())).thenThrow(new RuntimeException("Token invalid"));
        boolean valid = Utility.validTokeHandler("token", jwtService, authUser);
        assertFalse(valid);

    }
}

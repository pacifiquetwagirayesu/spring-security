package com.pacifique.security.review.service;

import com.pacifique.security.review.security.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private JwtService jwtService;
    private AuthUser authUser;
    private JwtBuilder jwtBuilder;

    @BeforeEach
    void setUp() throws Exception {
        authUser = mock(AuthUser.class);
        jwtBuilder = mock(JwtBuilder.class);
        jwtService = new JwtService();


        Field declaredField = JwtService.class.getDeclaredField("expiration");
        Field jwtSecretKey = JwtService.class.getDeclaredField("jwtSecretKey");
        Field refreshTokenExpiration = JwtService.class.getDeclaredField("refreshTokenExpiration");
        declaredField.setAccessible(true);
        jwtSecretKey.setAccessible(true);
        refreshTokenExpiration.setAccessible(true);

        declaredField.set(jwtService, 30000L);
        jwtSecretKey.set(jwtService, "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        refreshTokenExpiration.set(jwtService, 60000L);

        when(authUser.getUsername()).thenReturn("john.doe");
        when(authUser.getFirstName()).thenReturn("John");
        when(authUser.getLastName()).thenReturn("Doe");

    }

    @Test
    void generateTokenTest() {
        String generatedToken = jwtService.generateToken(authUser);
        System.out.println("generatedToken = " + generatedToken);
        assertNotNull(generatedToken);

    }

    @Test
    void generateRefreshTokenTest() {
        String generateRefreshToken = jwtService.generateRefreshToken(authUser);
        assertNotNull(generateRefreshToken);
    }

    @Test
    void getUsernameTest() throws Exception {
        Method extractAllClaims = JwtService.class.getDeclaredMethod("extractAllClaims", String.class);
        extractAllClaims.setAccessible(true);
        String generatedToken = jwtService.generateToken(authUser);
        Claims claims = (Claims) extractAllClaims.invoke(jwtService, generatedToken);
        assertEquals("john.doe", claims.get("sub"));
    }


    @Test
    void isTokenNotExpiredTest() throws Exception {
        Method isTokenExpired = JwtService.class.getDeclaredMethod("isTokenExpired", String.class);
        isTokenExpired.setAccessible(true);
        String generatedToken = jwtService.generateToken(authUser);
        boolean isValid = (boolean) isTokenExpired.invoke(jwtService, generatedToken);
        assertFalse(isValid);
    }

    @Test
    void isTokenValidTest() {
        String generatedToken = jwtService.generateToken(authUser);
        boolean tokenValid = jwtService.isTokenValid(generatedToken, authUser);
        assertTrue(tokenValid);
    }


    @Test
    void isTokenNotValidUsernameTest() {
        String generatedToken = jwtService.generateToken(authUser);
        when(authUser.getUsername()).thenReturn("john");
        boolean tokenValid = jwtService.isTokenValid(generatedToken, authUser);
        assertFalse(tokenValid);
    }

    @Test
    void isTokenNotValidExpiredTest() throws Exception {
        Field expiration = JwtService.class.getDeclaredField("expiration");
        expiration.setAccessible(true);
        expiration.set(jwtService, -600000L);
        String generatedToken = jwtService.generateToken(authUser);
        assertThrows(ExpiredJwtException.class, () -> jwtService.isTokenValid(generatedToken, authUser));
    }


    private void buildJwtToken(Map<String, Object> claims, AuthUser authUser, long expiration) throws Exception {

        when(jwtBuilder.claims(anyMap())).thenReturn(jwtBuilder);
        when(jwtBuilder.subject(anyString())).thenReturn(jwtBuilder);
        when(jwtBuilder.issuer(anyString())).thenReturn(jwtBuilder);
        when(jwtBuilder.issuedAt(any(Date.class))).thenReturn(jwtBuilder);
        when(jwtBuilder.expiration(any(Date.class))).thenReturn(jwtBuilder);
        when(jwtBuilder.signWith(any())).thenReturn(jwtBuilder);
        when(jwtBuilder.compact()).thenReturn("jwt-token");

        when(authUser.getUsername()).thenReturn("john.doe");
        when(authUser.getFirstName()).thenReturn("John");
        when(authUser.getLastName()).thenReturn("Doe");

        JwtService service = spy(new JwtService());
//        doReturn(mock(Key.class)).when(service).getSignedKey();
        try (MockedStatic<Jwts> jwtsMock = mockStatic(Jwts.class)) {
            jwtsMock.when(Jwts::builder).thenReturn(jwtBuilder);

            Method method = JwtService.class.getDeclaredMethod("buildJwtToken", Map.class, AuthUser.class, Long.class);
            method.setAccessible(true);

            String token = (String) method.invoke(service, claims, authUser, expiration);
            assertEquals("jwt-token", token);

        }
    }

    private void extractClaims(String token) throws Exception {
        JwtParserBuilder jwtParserBuilder = mock(JwtParserBuilder.class);
        JwtParser jwtParser = mock(JwtParser.class);
        Jws<Claims> jws = mock(Jws.class);
        when(jwtParserBuilder.setSigningKey(any(Key.class))).thenReturn(jwtParserBuilder);
        when(jwtParserBuilder.build()).thenReturn(jwtParser);
        when(jwtParser.parseSignedClaims(anyString())).thenReturn(jws);
        Claims claims = Jwts.claims().add(Map.of("role", "admin")).subject("username").build();
        when(jws.getPayload()).thenReturn(claims);

        JwtService service = spy(new JwtService());
//        doReturn(mock(Key.class)).when(service).getSignedKey();

        try (MockedStatic<Jwts> jwtsMock = mockStatic(Jwts.class)) {
            jwtsMock.when(Jwts::parser).thenReturn(jwtParserBuilder);

            Method method = JwtService.class.getDeclaredMethod("extractAllClaims", String.class);
            method.setAccessible(true);
            Claims claimsObj = (Claims) method.invoke(service, token);

        }
    }

}

package com.pacifique.security.review.service;

import com.pacifique.security.review.security.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JwtService implements IJwtService {
    @Value("${security.jwt.secret-key}")
    @Getter
    private String jwtSecretKey;
    @Getter
    @Value("${security.jwt.expiration}")
    private Long expiration;
    @Getter
    @Value("${security.jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    private final HttpServletRequest request;
    private final HttpServletResponse response;


    @Override
    public String generateToken(AuthUser authUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", authUser.getRole());
        claims.put("authorities", authUser.getAuthorities());
        return buildJwtToken(claims, authUser,this.expiration);
    }

    @Override
    public String generateRefreshToken(AuthUser authUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", authUser.getRole());
        claims.put("authorities", authUser.getAuthorities());
        return buildJwtToken(claims, authUser,this.refreshTokenExpiration);
    }



    public boolean isTokenValid(String token, AuthUser authUser) throws JwtException {
        String username = getUsername(token);
        return username.equals(authUser.getUsername()) && !isTokenExpired(token);
    }


    private  <T> T extractClaims(String token, Function<Claims, T> claimsResolver) throws JwtException {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    private  Claims extractAllClaims(String token) throws JwtException {
            return Jwts.parser()
                    .setSigningKey(getSignedKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

    }

    private Key getSignedKey() {
        byte[] decodedKey = Base64.getDecoder().decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    private String buildJwtToken(
            Map<String, Object> claims,
            AuthUser authUser,
            long expiration
    ) {

        return Jwts.builder()
                .claims(claims)
                .subject(authUser.getUsername())
                .issuer(authUser.getFirstName().concat(" ").concat(authUser.getLastName()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignedKey())
                .compact();
    }


    private boolean isTokenExpired(String token)  throws JwtException {
            return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    public String getUsername(String token) throws JwtException {
        return extractClaims(token, Claims::getSubject);
    }





}

package com.pacifique.security.review.service;

import com.pacifique.security.review.security.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface IJwtService {


    String generateToken(AuthUser authUser);

    String generateRefreshToken(AuthUser authUser);

    boolean isTokenValid(String token, AuthUser authUser) throws JwtException;

    String getUsername(String token);



    }

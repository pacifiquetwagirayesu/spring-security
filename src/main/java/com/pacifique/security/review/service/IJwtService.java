package com.pacifique.security.review.service;

import com.pacifique.security.review.model.User;
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


    String generateToken(AuthUser user);

    String generateRefreshToken(AuthUser user);

    boolean isTokenValid(String token, AuthUser user) throws JwtException;

    String getUsername(String token);



    }

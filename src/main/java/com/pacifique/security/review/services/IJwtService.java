package com.pacifique.security.review.services;

import com.pacifique.security.review.security.AuthUser;
import io.jsonwebtoken.JwtException;

public interface IJwtService {


    String generateToken(AuthUser user);

    String generateRefreshToken(AuthUser user);

    boolean isTokenValid(String token, AuthUser user) throws JwtException;

    String getUsername(String token);


}

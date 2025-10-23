package com.pacifique.security.review.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.pacifique.security.review.utils.HttpFilterErrorMessage.generateResponse;

@Component
@Slf4j
public class AuthCustomerFilter implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        int status = response.getStatus();

        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            generateResponse(response, request, new BadCredentialsException("Please login, Invalid username or password"));
            return;
        }

        if (status == HttpServletResponse.SC_UNAUTHORIZED) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            generateResponse(response, request, authException);
        } else if (status == HttpServletResponse.SC_FORBIDDEN) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            generateResponse(response, request, authException);
        }

    }

}

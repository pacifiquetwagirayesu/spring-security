package com.pacifique.security.review.security;

import com.pacifique.security.review.service.IJwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final IJwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    @Value("${security.jwt.super-admin}")
    private String jwtSuperAdmin;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")
                && header.substring(7).strip().equals(jwtSuperAdmin.strip())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.strip().substring(7);
            String username = jwtService.getUsername(token);
            AuthUser au = (AuthUser) userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token, au)) {
                var authentication = new UsernamePasswordAuthenticationToken(au,
                        null, Set.of(new SimpleGrantedAuthority(au.getRole()))
                );

                //authentication.setDetails(new WebAuthenticationDetails(request)); i don't need metadata
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

            log.info("Authenticated : {}", authenticated);
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Authorization header received: {}", header);
        doFilter(request, response, filterChain);

    }


}

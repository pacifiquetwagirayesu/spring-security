package com.pacifique.security.review.security;

import com.pacifique.security.review.services.IJwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
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
        doFilter(request, response, filterChain);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String servletPath = request.getServletPath();
        return servletPath.equals("/api/v1/admin");
    }
}

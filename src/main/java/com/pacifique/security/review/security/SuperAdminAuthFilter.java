package com.pacifique.security.review.security;

import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.model.User;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class SuperAdminAuthFilter extends OncePerRequestFilter {

    private final PasswordEncoder passwordEncoder;
    @Value("${security.jwt.super-admin}")
    private String jwtSuperAdmin;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ") && header.strip().substring(7).equals(jwtSuperAdmin.strip())) {
            var manager = new InMemoryUserDetailsManager();
            AuthUser user = AuthUser.getUser(User.builder()
                    .createdAt(LocalDateTime.now())
                    .id(100L)
                    .firstName("admin")
                    .lastName("admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin1234"))
                    .role(Role.SUPER_ADMIN.name())
                    .build());

            manager.createUser(user);

            var auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(),
                    Set.of(new SimpleGrantedAuthority(user.getRole())));

            SecurityContextHolder.getContext().setAuthentication(auth);

        }

        filterChain.doFilter(request, response);


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        return List.of("/api/v1/users", "/api/v1/products", "/api/v1/rentals").contains(servletPath);
    }
}

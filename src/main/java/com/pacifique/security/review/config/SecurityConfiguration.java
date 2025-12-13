package com.pacifique.security.review.config;

import com.pacifique.security.review.security.AuthCustomerFilter;
import com.pacifique.security.review.security.JwtAuthenticationFilter;
import com.pacifique.security.review.security.SuperAdminAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.pacifique.security.review.utils.ConstantsFields.WHITE_LIST_URL;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableAsync
public class SecurityConfiguration {
    private final AuthCustomerFilter authCustomerFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SuperAdminAuthFilter superAdminAuthFilter;
    private final LogoutHandler logoutHandler;

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {

        http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(authCustomerFilter));
        http.authorizeHttpRequests((authorize) -> {
//            authorize.requestMatchers(HttpMethod.GET,"/api/v1/users").access(hasAnyRole(ADMIN.name()));
//            authorize.requestMatchers(HttpMethod.GET,"/api/v1/users").access(new WebExpressionAuthorizationManager(
//                    "hasRole('ADMIN') or hasRole('MANAGER')"
//            ));
//            authorize.requestMatchers(HttpMethod.POST,"/api/v1/users").access(allOf(hasAnyRole("ADMIN"), hasAnyRole("MANAGER")));
            authorize.requestMatchers(WHITE_LIST_URL).permitAll();
            authorize.requestMatchers("/api/*/admin/**").authenticated();
            authorize.requestMatchers("/api/*/auth/**").permitAll();
            authorize.requestMatchers(HttpMethod.GET, "/api/*/products/**").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll();

            authorize.requestMatchers("/api/*/users/**").authenticated();
            authorize.requestMatchers(HttpMethod.POST, "/api/*/products/**").authenticated();
            authorize.requestMatchers(HttpMethod.PATCH, "/api/*/products/**").authenticated();
            authorize.requestMatchers(HttpMethod.PUT, "/api/*/products/**").authenticated();
            authorize.requestMatchers(HttpMethod.DELETE, "/api/*/products/**").authenticated();
            authorize.requestMatchers("/hello/**").authenticated();
            authorize.anyRequest().denyAll();
        });

        http.addFilterAt(jwtAuthenticationFilter, BasicAuthenticationFilter.class);
        http.addFilterAfter(superAdminAuthFilter, BasicAuthenticationFilter.class);

        http.csrf(AbstractHttpConfigurer::disable);
        http.logout(httpLogout -> httpLogout.
                logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) ->
                        SecurityContextHolder.clearContext()));

        return http.build();
    }


}

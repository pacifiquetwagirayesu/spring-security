package com.pacifique.security.review.config;

import com.pacifique.security.review.security.AuthCustomerFilter;
import com.pacifique.security.review.security.JwtAuthenticationFilter;
import com.pacifique.security.review.security.SuperAdminAuthFilter;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableAsync
public class AppSecurityConfiguration {
    private final AuthCustomerFilter authCustomerFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SuperAdminAuthFilter superAdminAuthFilter;

    @Bean
    protected SecurityFilterChain config(HttpSecurity http)throws Exception {

        http.exceptionHandling(ehc-> ehc.authenticationEntryPoint(authCustomerFilter));
        http.authorizeHttpRequests((authorize)->{
//            authorize.requestMatchers(HttpMethod.GET,"/api/v1/users").access(hasAnyRole(ADMIN.name()));
//            authorize.requestMatchers(HttpMethod.GET,"/api/v1/users").access(new WebExpressionAuthorizationManager(
//                    "hasRole('ADMIN') or hasRole('MANAGER')"
//            ));
//            authorize.requestMatchers(HttpMethod.POST,"/api/v1/users").access(allOf(hasAnyRole("ADMIN"), hasAnyRole("MANAGER")));
            authorize.requestMatchers("/admin/**").permitAll();
            authorize.requestMatchers("/api/*/auth/**").permitAll();
            authorize.requestMatchers(HttpMethod.GET,"/api/*/products/**").permitAll();
            authorize.requestMatchers(HttpMethod.POST,"/api/v1/users").permitAll();

            authorize.requestMatchers(HttpMethod.GET,"/api/*/users").authenticated();
            authorize.requestMatchers(HttpMethod.POST,"/api/*/products/**").authenticated();
            authorize.requestMatchers(HttpMethod.PATCH,"/api/*/products/**").authenticated();
            authorize.requestMatchers(HttpMethod.PUT,"/api/*/products/**").authenticated();
            authorize.requestMatchers(HttpMethod.DELETE,"/api/*/products/**").authenticated();
            authorize.anyRequest().denyAll();
        });

        http.addFilterAt(jwtAuthenticationFilter, BasicAuthenticationFilter.class);
        http.addFilterAfter(superAdminAuthFilter, BasicAuthenticationFilter.class);

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }


}

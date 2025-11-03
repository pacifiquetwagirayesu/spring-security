package com.pacifique.security.review.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;


@RequiredArgsConstructor
@Slf4j
@Deprecated
public class CustomerAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        AuthUser user = (AuthUser) userDetailsService.loadUserByUsername(email);

        if (passwordEncoder.matches(password, user.getPassword()) && email.equals(user.getUsername())
        ) {
            return new UsernamePasswordAuthenticationToken(user, user.getPassword(),
                    Set.of(new SimpleGrantedAuthority(user.getRole())));
        }

        throw new BadCredentialsException("Invalid email or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

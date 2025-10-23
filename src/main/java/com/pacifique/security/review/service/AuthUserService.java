package com.pacifique.security.review.service;

import com.pacifique.security.review.dto.UserLoginRequest;
import com.pacifique.security.review.dto.UserLoginResponse;
import com.pacifique.security.review.dto.UserResponse;
import com.pacifique.security.review.exception.InvalidToken;
import com.pacifique.security.review.exception.UserNotFound;
import com.pacifique.security.review.model.Token;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.repository.ITokenRepository;
import com.pacifique.security.review.repository.IUserRepository;
import com.pacifique.security.review.security.AuthUser;
import com.pacifique.security.review.security.UserDetailsServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.pacifique.security.review.utils.TypeConverter.convertUserResponse;
import static com.pacifique.security.review.utils.TypeConverter.loginUserResponse;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AuthUserService implements IAuthUserService {

    private final ITokenRepository tokenRepository;
    private final IUserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService jwtService;

    @Override
    public UserLoginResponse logInUser(UserLoginRequest req) {
        AuthUser authUser = userDetailsService.loadUserByUsername(req.username());
        Token userToken = tokenRepository.findByUserId(authUser.getId()).orElseThrow(()-> new InvalidToken("Invalid token"));

        if (!passwordEncoder.matches(req.password(), authUser.getPassword())) {
            throw new UserNotFound("Please check your username and password");
        }

        try {
            if (jwtService.isTokenValid(userToken.getToken(), authUser)) {
                return loginUserResponse(authUser, userToken);
            }
        } catch (Exception e) {
            log.info("Authentication failed: {}", e.getMessage());
        }

        log.info("deleted expired token");
        tokenRepository.delete(userToken);

        log.info("loading a new token for user {}", authUser.getId());
        String token = jwtService.generateToken(authUser);
        String refreshToken = jwtService.generateRefreshToken(authUser);


        var savedToken = tokenRepository.save(Token.builder().userId(authUser
                .getId()).token(token).refreshToken(refreshToken).build());

        log.info("Access Token loaded: {}", savedToken.getToken());
        return loginUserResponse(authUser, savedToken);
    }


    @Override
    @PostAuthorize("returnObject.email == authentication.principal.username")
    public UserResponse loggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(()
                -> new UsernameNotFoundException("User not found"));
        return convertUserResponse().apply(user);
    }
}

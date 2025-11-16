package com.pacifique.security.review.services;

import com.pacifique.security.review.dto.UserLoginRequest;
import com.pacifique.security.review.dto.UserLoginResponse;
import com.pacifique.security.review.dto.UserResponse;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.pacifique.security.review.utils.Utility.convertUserResponse;
import static com.pacifique.security.review.utils.Utility.loginUserResponse;
import static com.pacifique.security.review.utils.Utility.validTokeHandler;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AuthUserService implements IAuthUserService {

    private final ITokenRepository tokenRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public UserLoginResponse logInUser(UserLoginRequest req) {
        AuthUser au = (AuthUser) userDetailsService.loadUserByUsername(req.username());

        if (!passwordEncoder.matches(req.password(), au.getPassword())) {
            throw new UserNotFound("Please check your username and password");
        }

        var tk = tokenRepository.findByUserId(au.getId());

        if (tk.isPresent() && validTokeHandler(tk.get().getToken(), jwtService, au))
            return loginUserResponse(au, tk.get());

        tk.ifPresent(tokenRepository::delete);
        Token token = tokenRepository.save(Token.builder()
                .userId(au.getId())
                .token(jwtService.generateToken(au))
                .refreshToken(jwtService.generateRefreshToken(au)).build());

        log.info("loading a new token for user {}", au.getId());
        log.info("Access Token loaded: {}", token.getToken());
        return loginUserResponse(au, token);
    }


    @Override
    @PostAuthorize("returnObject.email == authentication.principal.username")
    public UserResponse loggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(()
                -> new UserNotFound("User not found"));
        return convertUserResponse().apply(user);
    }
}

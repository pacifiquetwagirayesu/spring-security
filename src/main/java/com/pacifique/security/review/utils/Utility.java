package com.pacifique.security.review.utils;

import com.pacifique.security.review.dto.ProductResponse;
import com.pacifique.security.review.dto.UserLoginResponse;
import com.pacifique.security.review.dto.UserResponse;
import com.pacifique.security.review.model.Product;
import com.pacifique.security.review.model.Token;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.security.AuthUser;
import com.pacifique.security.review.service.IJwtService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.function.Function;

@Slf4j
public class Utility {
    public Utility() throws Exception {
        throw new Exception("Invalid action, Utility class");
    }


    public static Function<User, UserResponse> convertUserResponse() {
        return (user -> new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getPermissions(),
                user.getCreatedAt(),
                user.getUpdatedAt()));
    }

    public static Function<Product, ProductResponse> convertProductResponse() {
        return (product -> new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                convertUserResponse().apply(product.getOwner()),
                product.getCreatedAt(),
                product.getUpdatedAt()
        ));
    }

    public static UserLoginResponse loginUserResponse(AuthUser user, Token tokenObj) {
        return new UserLoginResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                Map.of("accessToken", tokenObj.getToken(), "refreshToken", tokenObj.getRefreshToken()));
    }

    public static boolean validTokeHandler(String token, IJwtService jwtService, AuthUser authUser) {
        try {
            return jwtService.isTokenValid(token, authUser);
        } catch (Exception e) {
            log.error("validTokeHandler exception", e);
        }
        return false;
    }


}

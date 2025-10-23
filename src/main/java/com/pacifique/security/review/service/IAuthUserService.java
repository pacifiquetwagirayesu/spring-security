package com.pacifique.security.review.service;

import com.pacifique.security.review.dto.UserLoginRequest;
import com.pacifique.security.review.dto.UserLoginResponse;
import com.pacifique.security.review.dto.UserResponse;

public interface IAuthUserService {
    UserLoginResponse logInUser(UserLoginRequest req);
    UserResponse loggedInUser();
}

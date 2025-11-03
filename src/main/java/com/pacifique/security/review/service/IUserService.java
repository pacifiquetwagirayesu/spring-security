package com.pacifique.security.review.service;

import com.pacifique.security.review.dto.UserRequest;
import com.pacifique.security.review.dto.UserResponse;

import java.util.Map;


public interface IUserService {
    String createUser(UserRequest req);

    Iterable<UserResponse> getAllUsers();

    Map<String, String> updateUserRole(Long id, String role);

    String deleteUserById(long id);
}

package com.pacifique.security.review.service;

import com.pacifique.security.review.dto.UserRequest;
import com.pacifique.security.review.dto.UserResponse;


public interface IUserService {
    String createUser(UserRequest req);

    Iterable<UserResponse> getAllUsers();

    String updateUserRole(Long id, String role);

    String deleteUserById(long id);
}

package com.pacifique.security.review.repository;

import com.pacifique.security.review.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {
    @NonNull
    List<User> findAll();
    Optional<User> findByEmail(String email);
}

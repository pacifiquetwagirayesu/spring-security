package com.pacifique.security.review.repository;

import com.pacifique.security.review.model.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITokenRepository extends CrudRepository<Token, Long> {
    Optional<Token> findByUserId(long userId);
}

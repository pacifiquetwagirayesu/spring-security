package com.pacifique.security.review.repository;

import com.pacifique.security.review.model.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRentalRepository extends CrudRepository<Rental, Long> {
}

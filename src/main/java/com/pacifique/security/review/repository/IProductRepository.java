package com.pacifique.security.review.repository;

import com.pacifique.security.review.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    @Query(value = " SELECT p FROM Product p WHERE p.owner.email=?#{authentication.name} ")
    Page<Product> findAllByOwner_Email(Pageable pageable);

}

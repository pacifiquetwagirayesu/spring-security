package com.pacifique.security.review.service;

import com.pacifique.security.review.repository.IRentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RentalService implements IRentalService {
    private final IRentalRepository rentalRepository;
}

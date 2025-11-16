package com.pacifique.security.review.services;

import com.pacifique.security.review.repository.IRentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RentalService implements IRentalService {
    private final IRentalRepository rentalRepository;
}

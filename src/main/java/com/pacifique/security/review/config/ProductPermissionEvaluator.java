package com.pacifique.security.review.config;

import com.pacifique.security.review.repository.IProductRepository;
import com.pacifique.security.review.security.AuthUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductPermissionEvaluator implements PermissionEvaluator {
    private final IProductRepository productRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        long id = Long.parseLong(targetId.toString());
        String[] permissions = permission.toString().split(",");
        var user = (AuthUser) authentication.getPrincipal();

        for (String p : permissions) {
            boolean anyMatch = user.getAuthorities().stream().anyMatch(authority ->
                    authority.getAuthority().equals(p));
            if (anyMatch) return true;
        }

        var owner = productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Product with id: %s not found ".formatted(id))).getOwner();

        return owner.getEmail().equals(user.getUsername());
    }
}

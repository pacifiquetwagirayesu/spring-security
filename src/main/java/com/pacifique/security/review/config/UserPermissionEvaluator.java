package com.pacifique.security.review.config;

import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

import static com.pacifique.security.review.utils.ConstantsFields.PREFIX;

@Component
@RequiredArgsConstructor
public class UserPermissionEvaluator implements PermissionEvaluator {
    private final IUserRepository userRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            String role = grantedAuthority.getAuthority().replace(PREFIX, "");
            return Role.valueOf(role).hasPermission(permission.toString());
        }
        return false;
    }

    /**
     *
     * @param authentication represents the user in question. Should not be null.
     * @param targetId       the identifier for the object instance (usually a Long)
     * @param targetType     a String representing the target's type (usually a Java
     *                       classname). Not null.
     * @param permission     a representation of the permission object as supplied by the
     *                       expression system. Not null.
     * @return boolean
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        long id = Long.parseLong(targetId.toString());
        boolean isAllowed = false;

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority().replace(PREFIX, "");
            isAllowed = Role.valueOf(role).hasPermission(permission.toString());
        }

        userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id %d not found", id)));

        return isAllowed;
    }
}

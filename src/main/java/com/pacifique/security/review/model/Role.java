package com.pacifique.security.review.model;

import lombok.Getter;

import java.util.Collections;
import java.util.Set;

import static com.pacifique.security.review.utils.ConstantsFields.ARCHIVE;
import static com.pacifique.security.review.utils.ConstantsFields.DELETE;
import static com.pacifique.security.review.utils.ConstantsFields.READ;
import static com.pacifique.security.review.utils.ConstantsFields.WRITE;

@Getter
public enum Role {

    SUPER_ADMIN(Set.of(READ, WRITE, DELETE, ARCHIVE)) {
        @Override
        public Set<String> getPermissions() {
            return Set.of(READ, WRITE, DELETE, ARCHIVE);
        }
    },
    ADMIN(Set.of(DELETE, WRITE, READ)) {
        @Override
        public Set<String> getPermissions() {
            return Set.of(DELETE, WRITE, READ);
        }
    }, MANAGER(Set.of(WRITE, READ)) {
        @Override
        public Set<String> getPermissions() {
            return Set.of(WRITE, READ);
        }
    }, USER(Set.of(READ)) {
        @Override
        public Set<String> getPermissions() {
            return Collections.singleton(READ);
        }
    };

    private final Set<String> permissions;

    Role(Set<String> permissions) {
        this.permissions = permissions;
    }


    public abstract Set<String> getPermissions();

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }


}

package com.pacifique.security.review.security;

import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pacifique.security.review.utils.PermissionConstants.PREFIX;

@AllArgsConstructor
@Builder
@Getter
public class AuthUser implements UserDetails {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String role;

    Set<String> permissions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }




    protected static AuthUser getUser(User user) {
       return AuthUser.builder()
               .username(user.getEmail())
               .id(user.getId())
               .firstName(user.getFirstName())
               .lastName(user.getLastName())
               .password(user.getPassword())
               .role(PREFIX.concat(Role.valueOf(user.getRole()).name()))
               .permissions(user.getPermissions())
               .build();
    }


    @Override
    public String toString() {
        return "AuthUser{" +
                "id=" + id +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", permissions=" + permissions +
                '}';
    }
}

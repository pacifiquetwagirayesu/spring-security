package com.pacifique.security.review.services;

import com.pacifique.security.review.dto.UserRequest;
import com.pacifique.security.review.dto.UserResponse;
import com.pacifique.security.review.exception.UserNotFound;
import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.proxies.IEmailProxies;
import com.pacifique.security.review.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

import static com.pacifique.security.review.utils.Utility.convertUserResponse;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IEmailProxies newAccountEmailServiceProxy;
    private final IEmailProxies updateAccountEmailServiceProxy;

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public Iterable<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> convertUserResponse().apply(user)).toList();
    }

    @Override
    public String createUser(UserRequest req) {
        User user = User.builder()
                .firstName(req.firstName())
                .lastName(req.lastName())
                .email(req.email())
                .password(passwordEncoder.encode(req.password()))
                .role(Role.valueOf(req.role()).name())
                .permissions(Role.valueOf(req.role()).getPermissions())
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        log.info("User created: {}", user.getEmail());
        newAccountEmailServiceProxy.accountActivityNotification(user);

        return "successfully created";
    }


    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Map<String, String> updateUserRole(Long id, String role) {
        String roleUpperCase = role.toUpperCase();
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound("user not found"));

        if (!(Role.valueOf(roleUpperCase).toString().equals(user.getRole()))) {
            user.setRole(roleUpperCase);
            user.setPermissions(Role.valueOf(roleUpperCase).getPermissions());
            user.setUpdatedAt(LocalDateTime.now());
            User saved = userRepository.save(user);
            log.info("User role updated: {}", saved.getRole());
            updateAccountEmailServiceProxy.accountActivityNotification(user);

            return Map.of("success", "user updated");
        }
        return Map.of();

    }


//    @PreAuthorize("hasPermission('user','delete')")
//    @PostAuthorize("hasPermission(returnObject,'delete')")
    @PreAuthorize("hasPermission(#id,'user','delete')")
    @Override
    public String deleteUserById(long id) {
        userRepository.deleteById(id);
        return "deleted";
    }
}

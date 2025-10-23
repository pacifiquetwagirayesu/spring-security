package com.pacifique.security.review.service;

import com.pacifique.security.review.dto.UserRequest;
import com.pacifique.security.review.dto.UserResponse;
import com.pacifique.security.review.model.Role;
import com.pacifique.security.review.model.User;
import com.pacifique.security.review.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.pacifique.security.review.utils.TypeConverter.convertUserResponse;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public Iterable<UserResponse> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication: {}", authentication);
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

        emailService.sendEmail(user, "new user created");
        return "successfully created";
    }


    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String updateUserRole(Long id, String role) {
        String roleUpperCase = role.toUpperCase();
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if (!(Role.valueOf(roleUpperCase).toString().equals(user.getRole()))) {
            user.setRole(role.toUpperCase());
            user.setPermissions(Role.valueOf(roleUpperCase).getPermissions());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
            emailService.sendEmail(user, "update");
            return "{\"success\" :\"user updated\"}";
        }
        return null;

    }


    @Override
//    @PreAuthorize("hasPermission('user','delete')")
    @PreAuthorize("hasPermission(#id,'user','delete')")
//    @PostAuthorize("hasPermission(returnObject,'delete')")
    public String deleteUserById(long id) {
        userRepository.deleteById(id);
        return "deleted";
    }
}

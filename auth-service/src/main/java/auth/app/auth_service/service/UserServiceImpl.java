package auth.app.auth_service.service;

import java.time.Instant;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import auth.app.auth_service.domain.dto.CreateUserRequest;
import auth.app.auth_service.domain.dto.LoginResponse;
import auth.app.auth_service.domain.dto.UserLoginRequest;
import auth.app.auth_service.domain.dto.UserResponse;
import auth.app.auth_service.domain.entity.User;
import auth.app.auth_service.domain.enums.UserRole;
import auth.app.auth_service.jwt.JwtService;
import auth.app.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponse signUp(CreateUserRequest request) {
        User user = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(UserRole.USER)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
        log.info("Creating user with id: {}", user.getId());

        UserResponse userResponse = UserResponse.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .role(user.getRole())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();

        userRepository.save(user);

        return userResponse;
    }

    @Override
    public LoginResponse login(UserLoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated()) {
            // Extract role from authentication authorities
            String roleString = authentication.getAuthorities().iterator().next().getAuthority();
            // Remove "ROLE_" prefix if present (Spring Security adds it)
            if (roleString.startsWith("ROLE_")) {
                roleString = roleString.substring(5);
            }
            UserRole role = UserRole.valueOf(roleString);
            String token = jwtService.generateToken(request.getUsername(), role);
            return LoginResponse.builder().token(token).build();
        }

        throw new RuntimeException("Authentication failed");
    }

    @Override
    public UserResponse findById(Long id) {
        log.info("Finding user with id: {}", id);
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        UserResponse userResponse = UserResponse.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .role(user.getRole())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();

        return userResponse;
    }
}

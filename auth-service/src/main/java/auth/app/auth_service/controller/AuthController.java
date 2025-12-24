package auth.app.auth_service.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import auth.app.auth_service.domain.dto.CreateUserRequest;
import auth.app.auth_service.domain.dto.LoginResponse;
import auth.app.auth_service.domain.dto.UserLoginRequest;
import auth.app.auth_service.domain.dto.UserResponse;
import auth.app.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody UserLoginRequest request) {
        return userService.login(request);
    }

    @PostMapping("/signup")
    public UserResponse signUp(@Valid @RequestBody CreateUserRequest request) {
        return userService.signUp(request);
    }

    @GetMapping("/health")
    public String health() {
        return "Auth Service is running";
    }

    @GetMapping("/test-auth")
    public String testAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "Unauthorized";
        }
        return authentication.getName();
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }
}

package auth.app.auth_service.service;

import auth.app.auth_service.domain.dto.CreateUserRequest;
import auth.app.auth_service.domain.dto.LoginResponse;
import auth.app.auth_service.domain.dto.UserLoginRequest;
import auth.app.auth_service.domain.dto.UserResponse;

public interface UserService {
    UserResponse signUp(CreateUserRequest request);
    UserResponse findById(Long id);
    LoginResponse login(UserLoginRequest request);
}

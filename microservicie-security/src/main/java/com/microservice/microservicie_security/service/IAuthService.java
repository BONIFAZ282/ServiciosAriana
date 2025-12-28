package com.microservice.microservicie_security.service;


import com.microservice.microservicie_security.dto.*;

public interface IAuthService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest);
    void logout(String token);
    void changePassword(Integer usuarioSecurityId, ChangePasswordRequest changePasswordRequest);
    PermissionCheckResponse hasPermission(PermissionCheckRequest permissionCheckRequest);
}
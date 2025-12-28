package com.microservice.microservicie_security.controller;

import com.microservice.microservicie_security.dto.*;
import com.microservice.microservicie_security.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = authService.login(loginRequest);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Error de autenticación", e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            AuthResponse authResponse = authService.refreshToken(refreshTokenRequest);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Error al refrescar token", e.getMessage()));
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenResponse> validateToken(@Valid @RequestBody ValidateTokenRequest validateTokenRequest) {
        try {
            ValidateTokenResponse response = authService.validateToken(validateTokenRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ValidateTokenResponse errorResponse = new ValidateTokenResponse(false, "Error al validar token: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = extractTokenFromHeader(authorizationHeader);
            authService.logout(token);
            return ResponseEntity.ok(new MessageResponse("Logout exitoso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error al cerrar sesión", e.getMessage()));
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            String token = extractTokenFromHeader(authorizationHeader);
            ValidateTokenRequest validateRequest = new ValidateTokenRequest(token);
            ValidateTokenResponse tokenResponse = authService.validateToken(validateRequest);

            if (!tokenResponse.getValid()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Token inválido", "Debe autenticarse nuevamente"));
            }

            authService.changePassword(tokenResponse.getUsuarioSecurityId(), changePasswordRequest);
            return ResponseEntity.ok(new MessageResponse("Contraseña cambiada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error al cambiar contraseña", e.getMessage()));
        }
    }

    @PostMapping("/check-permission")
    public ResponseEntity<PermissionCheckResponse> checkPermission(@Valid @RequestBody PermissionCheckRequest permissionCheckRequest) {
        try {
            PermissionCheckResponse response = authService.hasPermission(permissionCheckRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            PermissionCheckResponse errorResponse = new PermissionCheckResponse(
                    false,
                    "Error al verificar permisos: " + e.getMessage(),
                    permissionCheckRequest.getRecurso(),
                    permissionCheckRequest.getAccion(),
                    permissionCheckRequest.getUsuarioId()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Endpoint para que otros microservicios validen tokens de forma simple
    @GetMapping("/validate-token")
    public ResponseEntity<ValidateTokenResponse> validateTokenGet(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = extractTokenFromHeader(authorizationHeader);
            ValidateTokenRequest validateRequest = new ValidateTokenRequest(token);
            ValidateTokenResponse response = authService.validateToken(validateRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ValidateTokenResponse errorResponse = new ValidateTokenResponse(false, "Error al validar token: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    // Endpoint para verificar permisos específicos con token en header
    @GetMapping("/check-permission/{recurso}/{accion}")
    public ResponseEntity<PermissionCheckResponse> checkPermissionWithToken(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String recurso,
            @PathVariable String accion) {
        try {
            String token = extractTokenFromHeader(authorizationHeader);
            ValidateTokenRequest validateRequest = new ValidateTokenRequest(token);
            ValidateTokenResponse tokenResponse = authService.validateToken(validateRequest);

            if (!tokenResponse.getValid()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new PermissionCheckResponse(false, "Token inválido", recurso, accion, null));
            }

            PermissionCheckRequest permissionRequest = new PermissionCheckRequest(
                    tokenResponse.getUsuarioId(),
                    recurso,
                    accion
            );

            PermissionCheckResponse response = authService.hasPermission(permissionRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            PermissionCheckResponse errorResponse = new PermissionCheckResponse(
                    false,
                    "Error al verificar permisos: " + e.getMessage(),
                    recurso,
                    accion,
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<MessageResponse> health() {
        return ResponseEntity.ok(new MessageResponse("Security Service está funcionando correctamente"));
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new RuntimeException("Token no proporcionado o formato inválido");
    }
}
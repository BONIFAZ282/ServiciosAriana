package com.microservice.microservicie_security.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private LocalDateTime expiresAt;

    // Información del usuario
    private Integer usuarioId;
    private Integer usuarioSecurityId;
    private String username;

    // Roles y permisos
    private List<String> roles;
    private List<String> permisos;

    public AuthResponse(String accessToken, String refreshToken, LocalDateTime expiresAt,
                        Integer usuarioId, Integer usuarioSecurityId, String username,
                        List<String> roles, List<String> permisos) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
        this.usuarioId = usuarioId;
        this.usuarioSecurityId = usuarioSecurityId;
        this.username = username;
        this.roles = roles;
        this.permisos = permisos;
    }
}
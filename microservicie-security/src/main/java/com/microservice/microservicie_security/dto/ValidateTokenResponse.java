package com.microservice.microservicie_security.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenResponse {

    private Boolean valid;
    private String message;
    private Integer usuarioId;
    private Integer usuarioSecurityId;
    private String username;
    private List<String> roles;
    private List<String> permisos;
    private LocalDateTime expiresAt;

    // Constructor para token válido
    public ValidateTokenResponse(Boolean valid, Integer usuarioId, Integer usuarioSecurityId,
                                 String username, List<String> roles, List<String> permisos,
                                 LocalDateTime expiresAt) {
        this.valid = valid;
        this.usuarioId = usuarioId;
        this.usuarioSecurityId = usuarioSecurityId;
        this.username = username;
        this.roles = roles;
        this.permisos = permisos;
        this.expiresAt = expiresAt;
        this.message = "Token válido";
    }

    // Constructor para token inválido
    public ValidateTokenResponse(Boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }
}
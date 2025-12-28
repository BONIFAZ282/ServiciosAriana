package com.microservice.microservicie_security.service;

import com.microservice.microservicie_security.entity.UsuarioSecurity;
import io.jsonwebtoken.Claims;

import java.time.LocalDateTime;
import java.util.List;

public interface IJwtService {
    String generateAccessToken(UsuarioSecurity usuario, List<String> roles, List<String> permisos);
    String generateRefreshToken(UsuarioSecurity usuario);
    Claims extractClaims(String token);
    String extractUsername(String token);
    Integer extractUsuarioId(String token);
    Integer extractUsuarioSecurityId(String token);
    List<String> extractRoles(String token);
    List<String> extractPermisos(String token);
    LocalDateTime extractExpiration(String token);
    Boolean isTokenExpired(String token);
    Boolean isTokenValid(String token);
    String hashToken(String token);
}
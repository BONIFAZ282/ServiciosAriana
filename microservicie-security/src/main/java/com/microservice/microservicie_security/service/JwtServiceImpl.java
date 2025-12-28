package com.microservice.microservicie_security.service;


import com.microservice.microservicie_security.entity.UsuarioSecurity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class JwtServiceImpl implements IJwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generateAccessToken(UsuarioSecurity usuario, List<String> roles, List<String> permisos) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("usuarioId", usuario.getNUsuarioId());
        claims.put("usuarioSecurityId", usuario.getNUsuarioSecurityId());
        claims.put("roles", roles);
        claims.put("permisos", permisos);
        claims.put("type", "ACCESS");

        return createToken(claims, usuario.getCUsername(), jwtExpiration);
    }

    @Override
    public String generateRefreshToken(UsuarioSecurity usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("usuarioId", usuario.getNUsuarioId());
        claims.put("usuarioSecurityId", usuario.getNUsuarioSecurityId());
        claims.put("type", "REFRESH");

        return createToken(claims, usuario.getCUsername(), refreshExpiration);
    }

    private String createToken(Map<String, Object> claims, String subject, Long expiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    @Override
    public Integer extractUsuarioId(String token) {
        return extractClaims(token).get("usuarioId", Integer.class);
    }

    @Override
    public Integer extractUsuarioSecurityId(String token) {
        return extractClaims(token).get("usuarioSecurityId", Integer.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return extractClaims(token).get("roles", List.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> extractPermisos(String token) {
        return extractClaims(token).get("permisos", List.class);
    }

    @Override
    public LocalDateTime extractExpiration(String token) {
        Date expiration = extractClaims(token).getExpiration();
        return expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @Override
    public Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).isBefore(LocalDateTime.now());
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public Boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar hash del token", e);
        }
    }
}
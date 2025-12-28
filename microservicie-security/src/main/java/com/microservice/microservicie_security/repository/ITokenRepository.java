package com.microservice.microservicie_security.repository;

import com.microservice.microservicie_security.entity.Token;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ITokenRepository {
    Optional<Token> findValidToken(String tokenHash);
    List<Token> findTokensByUser(Integer usuarioSecurityId);
    void createToken(Integer usuarioSecurityId, String tokenHash, String tipo, LocalDateTime fechaExpiracion);
    void revokeToken(String tokenHash);
    void revokeAllTokensByUser(Integer usuarioSecurityId, String tipo);
    void cleanExpiredTokens();
}
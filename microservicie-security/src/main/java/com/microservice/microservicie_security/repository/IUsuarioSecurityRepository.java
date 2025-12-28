package com.microservice.microservicie_security.repository;

import com.microservice.microservicie_security.entity.UsuarioSecurity;

import java.util.Optional;

public interface IUsuarioSecurityRepository {
    Optional<UsuarioSecurity> findByUsername(String username);
    Optional<UsuarioSecurity> findByUsuarioId(Integer usuarioId);
    void save(UsuarioSecurity usuario);
    void updateLastLogin(Integer usuarioSecurityId);
    void updateFailedAttempts(Integer usuarioSecurityId, Integer intentos);
    void lockUser(Integer usuarioSecurityId, Integer minutos);
    void unlockUser(Integer usuarioSecurityId);
    void changePassword(Integer usuarioSecurityId, String passwordHash);
}
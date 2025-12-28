package com.microservice.microservicie_security.repository;


import com.microservice.microservicie_security.entity.UsuarioRol;

import java.util.List;

public interface IUsuarioRolRepository {
    void assignRoleToUser(Integer usuarioSecurityId, Integer rolId);
    void removeRoleFromUser(Integer usuarioSecurityId, Integer rolId);
    List<UsuarioRol> findUsersByRole(Integer rolId);
}
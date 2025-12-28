package com.microservice.microservicie_security.repository;

import com.microservice.microservicie_security.entity.Rol;

import java.util.List;
import java.util.Optional;

public interface IRolRepository {
    List<Rol> findAllActive();
    Optional<Rol> findByNombre(String nombre);
    List<Rol> findRolesByUsuarioId(Integer usuarioSecurityId);
    void save(Rol rol);
    void update(Integer rolId, String nombre, String descripcion);
    void delete(Integer rolId);
}
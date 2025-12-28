package com.microservice.microservicie_security.repository;


import com.microservice.microservicie_security.entity.Permiso;

import java.util.List;
import java.util.Optional;

public interface IPermisoRepository {
    List<Permiso> findAllActive();
    Optional<Permiso> findByRecursoAndAccion(String recurso, String accion);
    List<Permiso> findPermisosByUsuarioId(Integer usuarioSecurityId);
    List<Permiso> findPermisosByRolId(Integer rolId);
    Boolean hasPermission(Integer usuarioSecurityId, String recurso, String accion);
    void save(Permiso permiso);
}
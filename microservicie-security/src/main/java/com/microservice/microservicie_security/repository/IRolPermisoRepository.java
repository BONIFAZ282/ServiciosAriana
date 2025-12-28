package com.microservice.microservicie_security.repository;


public interface IRolPermisoRepository {
    void assignPermissionToRole(Integer rolId, Integer permisoId);
    void removePermissionFromRole(Integer rolId, Integer permisoId);
}
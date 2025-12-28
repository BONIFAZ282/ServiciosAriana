package com.microservice.microservicie_security.repository;


import com.microservice.microservicie_security.repository.StoredProcedure.StoredProcedureSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class RolPermisoRepositoryImpl implements IRolPermisoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RolPermisoRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void assignPermissionToRole(Integer rolId, Integer permisoId) {
        jdbcTemplate.update(
                StoredProcedureSecurity.INS_ASIGNAR_PERMISO,
                rolId,
                permisoId
        );
    }

    @Override
    public void removePermissionFromRole(Integer rolId, Integer permisoId) {
        jdbcTemplate.update(
                StoredProcedureSecurity.UPD_REMOVER_PERMISO,
                rolId,
                permisoId
        );
    }
}
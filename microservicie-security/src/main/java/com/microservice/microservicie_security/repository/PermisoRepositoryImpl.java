package com.microservice.microservicie_security.repository;

import com.microservice.microservicie_security.entity.Permiso;
import com.microservice.microservicie_security.repository.StoredProcedure.StoredProcedureSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class PermisoRepositoryImpl implements IPermisoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PermisoRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Permiso> findAllActive() {
        return jdbcTemplate.query(
                StoredProcedureSecurity.SEL_PERMISOS,
                new BeanPropertyRowMapper<>(Permiso.class)
        );
    }

    @Override
    public Optional<Permiso> findByRecursoAndAccion(String recurso, String accion) {
        try {
            List<Permiso> permisos = jdbcTemplate.query(
                    StoredProcedureSecurity.SEL_PERMISO_POR_RECURSO_ACCION,
                    new BeanPropertyRowMapper<>(Permiso.class),
                    recurso, accion
            );
            return permisos.isEmpty() ? Optional.empty() : Optional.of(permisos.get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Permiso> findPermisosByUsuarioId(Integer usuarioSecurityId) {
        return jdbcTemplate.query(
                StoredProcedureSecurity.SEL_PERMISOS_DE_USUARIO,
                new BeanPropertyRowMapper<>(Permiso.class),
                usuarioSecurityId
        );
    }

    @Override
    public List<Permiso> findPermisosByRolId(Integer rolId) {
        return jdbcTemplate.query(
                StoredProcedureSecurity.SEL_PERMISOS_DE_ROL,
                new BeanPropertyRowMapper<>(Permiso.class),
                rolId
        );
    }

    @Override
    public Boolean hasPermission(Integer usuarioSecurityId, String recurso, String accion) {
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    StoredProcedureSecurity.SEL_VALIDAR_PERMISO,
                    Boolean.class,
                    usuarioSecurityId, recurso, accion
            );
            return result != null ? result : false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void save(Permiso permiso) {
        jdbcTemplate.update(
                StoredProcedureSecurity.INS_PERMISO_NUEVO,
                permiso.getCRecurso(),
                permiso.getCAccion(),
                permiso.getCDescripcionPermiso()
        );
    }
}

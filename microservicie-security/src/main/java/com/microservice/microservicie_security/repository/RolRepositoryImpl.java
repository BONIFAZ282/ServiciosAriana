package com.microservice.microservicie_security.repository;

import com.microservice.microservicie_security.entity.Rol;
import com.microservice.microservicie_security.repository.StoredProcedure.StoredProcedureSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class RolRepositoryImpl implements IRolRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RolRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Rol> findAllActive() {
        return jdbcTemplate.query(
                StoredProcedureSecurity.SEL_ROLES,
                new BeanPropertyRowMapper<>(Rol.class)
        );
    }

    @Override
    public Optional<Rol> findByNombre(String nombre) {
        try {
            List<Rol> roles = jdbcTemplate.query(
                    StoredProcedureSecurity.SEL_ROL_POR_NOMBRE,
                    new BeanPropertyRowMapper<>(Rol.class),
                    nombre
            );
            return roles.isEmpty() ? Optional.empty() : Optional.of(roles.get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Rol> findRolesByUsuarioId(Integer usuarioSecurityId) {
        return jdbcTemplate.query(
                StoredProcedureSecurity.SEL_ROLES_DE_USUARIO,
                new BeanPropertyRowMapper<>(Rol.class),
                usuarioSecurityId
        );
    }

    @Override
    public void save(Rol rol) {
        jdbcTemplate.update(
                StoredProcedureSecurity.INS_ROL_NUEVO,
                rol.getCNombreRol(),
                rol.getCDescripcionRol()
        );
    }

    @Override
    public void update(Integer rolId, String nombre, String descripcion) {
        jdbcTemplate.update(
                StoredProcedureSecurity.UPD_MODIFICAR_ROL,
                rolId,
                nombre,
                descripcion
        );
    }

    @Override
    public void delete(Integer rolId) {
        jdbcTemplate.update(
                StoredProcedureSecurity.UPD_ELIMINAR_ROL,
                rolId
        );
    }
}
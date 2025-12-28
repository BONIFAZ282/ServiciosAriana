package com.microservice.microservicie_security.repository;

import com.microservice.microservicie_security.entity.UsuarioRol;
import com.microservice.microservicie_security.repository.StoredProcedure.StoredProcedureSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UsuarioRolRepositoryImpl implements IUsuarioRolRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsuarioRolRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void assignRoleToUser(Integer usuarioSecurityId, Integer rolId) {
        jdbcTemplate.update(
                StoredProcedureSecurity.INS_ASIGNAR_ROL,
                usuarioSecurityId,
                rolId
        );
    }

    @Override
    public void removeRoleFromUser(Integer usuarioSecurityId, Integer rolId) {
        jdbcTemplate.update(
                StoredProcedureSecurity.UPD_REMOVER_ROL,
                usuarioSecurityId,
                rolId
        );
    }

    @Override
    public List<UsuarioRol> findUsersByRole(Integer rolId) {
        return jdbcTemplate.query(
                StoredProcedureSecurity.SEL_USUARIOS_CON_ROL,
                new BeanPropertyRowMapper<>(UsuarioRol.class),
                rolId
        );
    }
}
package com.microservice.microservicie_security.repository;

import com.microservice.microservicie_security.entity.UsuarioSecurity;
import com.microservice.microservicie_security.repository.StoredProcedure.StoredProcedureSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UsuarioSecurityRepositoryImpl implements IUsuarioSecurityRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcCall simpleJdbcCall;

    @Autowired
    public UsuarioSecurityRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcCall = new SimpleJdbcCall(dataSource);
    }

    @Override
    public Optional<UsuarioSecurity> findByUsername(String username) {
        try {
            List<UsuarioSecurity> usuarios = jdbcTemplate.query(
                    StoredProcedureSecurity.SEL_USUARIO_POR_USERNAME,
                    new BeanPropertyRowMapper<>(UsuarioSecurity.class),
                    username
            );
            return usuarios.isEmpty() ? Optional.empty() : Optional.of(usuarios.get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UsuarioSecurity> findByUsuarioId(Integer usuarioId) {
        try {
            List<UsuarioSecurity> usuarios = jdbcTemplate.query(
                    StoredProcedureSecurity.SEL_USUARIO_POR_ID,
                    new BeanPropertyRowMapper<>(UsuarioSecurity.class),
                    usuarioId
            );
            return usuarios.isEmpty() ? Optional.empty() : Optional.of(usuarios.get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(UsuarioSecurity usuario) {
        jdbcTemplate.update(
                StoredProcedureSecurity.INS_USUARIO_NUEVO,
                usuario.getNUsuarioId(),
                usuario.getCUsername(),
                usuario.getCPasswordHash()
        );
    }

    @Override
    public void updateLastLogin(Integer usuarioSecurityId) {
        jdbcTemplate.update(
                StoredProcedureSecurity.UPD_ULTIMO_LOGIN,
                usuarioSecurityId
        );
    }

    @Override
    public void updateFailedAttempts(Integer usuarioSecurityId, Integer intentos) {
        jdbcTemplate.update(
                StoredProcedureSecurity.UPD_INTENTOS_FALLIDOS,
                usuarioSecurityId,
                intentos
        );
    }

    @Override
    public void lockUser(Integer usuarioSecurityId, Integer minutos) {
        jdbcTemplate.update(
                StoredProcedureSecurity.UPD_BLOQUEAR_USUARIO,
                usuarioSecurityId,
                minutos
        );
    }

    @Override
    public void unlockUser(Integer usuarioSecurityId) {
        jdbcTemplate.update(
                StoredProcedureSecurity.UPD_DESBLOQUEAR_USUARIO,
                usuarioSecurityId
        );
    }

    @Override
    public void changePassword(Integer usuarioSecurityId, String passwordHash) {
        jdbcTemplate.update(
                StoredProcedureSecurity.UPD_CAMBIAR_PASSWORD,
                usuarioSecurityId,
                passwordHash
        );
    }
}
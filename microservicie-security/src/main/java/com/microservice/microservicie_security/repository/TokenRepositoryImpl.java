package com.microservice.microservicie_security.repository;

import com.microservice.microservicie_security.entity.Token;
import com.microservice.microservicie_security.repository.StoredProcedure.StoredProcedureSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TokenRepositoryImpl implements ITokenRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TokenRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Token> findValidToken(String tokenHash) {
        try {
            List<Token> tokens = jdbcTemplate.query(
                    StoredProcedureSecurity.SEL_VALIDAR_TOKEN,
                    new BeanPropertyRowMapper<>(Token.class),
                    tokenHash
            );
            return tokens.isEmpty() ? Optional.empty() : Optional.of(tokens.get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Token> findTokensByUser(Integer usuarioSecurityId) {
        return jdbcTemplate.query(
                StoredProcedureSecurity.SEL_TOKENS_DE_USUARIO,
                new BeanPropertyRowMapper<>(Token.class),
                usuarioSecurityId
        );
    }

    @Override
    public void createToken(Integer usuarioSecurityId, String tokenHash, String tipo, LocalDateTime fechaExpiracion) {
        jdbcTemplate.update(
                StoredProcedureSecurity.INS_TOKEN_NUEVO,
                usuarioSecurityId,
                tokenHash,
                tipo,
                fechaExpiracion
        );
    }

    @Override
    public void revokeToken(String tokenHash) {
        jdbcTemplate.update(
                StoredProcedureSecurity.UPD_REVOCAR_TOKEN,
                tokenHash
        );
    }

    @Override
    public void revokeAllTokensByUser(Integer usuarioSecurityId, String tipo) {
        jdbcTemplate.update(
                StoredProcedureSecurity.UPD_REVOCAR_TOKENS_USUARIO,
                usuarioSecurityId,
                tipo
        );
    }

    @Override
    public void cleanExpiredTokens() {
        jdbcTemplate.update(StoredProcedureSecurity.DEL_TOKENS_EXPIRADOS);
    }
}
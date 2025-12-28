package com.microservice.microservicie_security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("Token")
public class Token {

    @Id
    @Column("nTokenId")
    private Integer nTokenId;

    @Column("nUsuarioSecurityId")
    private Integer nUsuarioSecurityId;

    @Column("cTokenHash")
    private String cTokenHash;

    @Column("cTipoToken")
    private String cTipoToken; // ACCESS, REFRESH

    @Column("dFechaExpiracion")
    private LocalDateTime dFechaExpiracion;

    @Column("dFechaCreacion")
    private LocalDateTime dFechaCreacion;

    @Column("bExpirado")
    private Boolean bExpirado;

    @Column("bRevocado")
    private Boolean bRevocado;

    public Token(Integer nUsuarioSecurityId, String cTokenHash, String cTipoToken, LocalDateTime dFechaExpiracion) {
        this.nUsuarioSecurityId = nUsuarioSecurityId;
        this.cTokenHash = cTokenHash;
        this.cTipoToken = cTipoToken;
        this.dFechaExpiracion = dFechaExpiracion;
        this.dFechaCreacion = LocalDateTime.now();
        this.bExpirado = false;
        this.bRevocado = false;
    }

    public boolean isTokenValid() {
        return !bExpirado && !bRevocado && dFechaExpiracion.isAfter(LocalDateTime.now());
    }

    public void revokeToken() {
        this.bRevocado = true;
    }

    public void expireToken() {
        this.bExpirado = true;
    }
}
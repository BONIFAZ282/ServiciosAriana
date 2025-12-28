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
@Table("UsuarioSecurity")
public class UsuarioSecurity {

    @Id
    @Column("nUsuarioSecurityId")
    private Integer nUsuarioSecurityId;

    @Column("nUsuarioId")
    private Integer nUsuarioId;

    @Column("cUsername")
    private String cUsername;

    @Column("cPasswordHash")
    private String cPasswordHash;

    @Column("dUltimoLogin")
    private LocalDateTime dUltimoLogin;

    @Column("nIntentosFallidos")
    private Integer nIntentosFallidos;

    @Column("dBloqueadoHasta")
    private LocalDateTime dBloqueadoHasta;

    @Column("dFechaCreacion")
    private LocalDateTime dFechaCreacion;

    @Column("dFechaModificacion")
    private LocalDateTime dFechaModificacion;

    @Column("bEstado")
    private Boolean bEstado;

    public UsuarioSecurity(Integer nUsuarioId, String cUsername, String cPasswordHash) {
        this.nUsuarioId = nUsuarioId;
        this.cUsername = cUsername;
        this.cPasswordHash = cPasswordHash;
        this.nIntentosFallidos = 0;
        this.dFechaCreacion = LocalDateTime.now();
        this.dFechaModificacion = LocalDateTime.now();
        this.bEstado = true;
    }

    public boolean isAccountLocked() {
        return dBloqueadoHasta != null && dBloqueadoHasta.isAfter(LocalDateTime.now());
    }

    public void incrementFailedAttempts() {
        this.nIntentosFallidos++;
        this.dFechaModificacion = LocalDateTime.now();
        if (this.nIntentosFallidos >= 3) {
            this.dBloqueadoHasta = LocalDateTime.now().plusMinutes(15); // Bloquear por 15 minutos
        }
    }

    public void resetFailedAttempts() {
        this.nIntentosFallidos = 0;
        this.dBloqueadoHasta = null;
        this.dFechaModificacion = LocalDateTime.now();
        this.dUltimoLogin = LocalDateTime.now();
    }
}
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
@Table("UsuarioRol")
public class UsuarioRol {

    @Id
    @Column("nUsuarioRolId")
    private Integer nUsuarioRolId;

    @Column("nUsuarioSecurityId")
    private Integer nUsuarioSecurityId;

    @Column("nRolId")
    private Integer nRolId;

    @Column("dFechaAsignacion")
    private LocalDateTime dFechaAsignacion;

    @Column("bEstado")
    private Boolean bEstado;

    public UsuarioRol(Integer nUsuarioSecurityId, Integer nRolId) {
        this.nUsuarioSecurityId = nUsuarioSecurityId;
        this.nRolId = nRolId;
        this.dFechaAsignacion = LocalDateTime.now();
        this.bEstado = true;
    }
}
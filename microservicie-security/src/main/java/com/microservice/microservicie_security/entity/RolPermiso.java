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
@Table("RolPermiso")
public class RolPermiso {

    @Id
    @Column("nRolPermisoId")
    private Integer nRolPermisoId;

    @Column("nRolId")
    private Integer nRolId;

    @Column("nPermisoId")
    private Integer nPermisoId;

    @Column("dFechaAsignacion")
    private LocalDateTime dFechaAsignacion;

    @Column("bEstado")
    private Boolean bEstado;

    public RolPermiso(Integer nRolId, Integer nPermisoId) {
        this.nRolId = nRolId;
        this.nPermisoId = nPermisoId;
        this.dFechaAsignacion = LocalDateTime.now();
        this.bEstado = true;
    }
}
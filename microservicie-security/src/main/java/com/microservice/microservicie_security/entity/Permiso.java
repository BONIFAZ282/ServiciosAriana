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
@Table("Permiso")
public class Permiso {

    @Id
    @Column("nPermisoId")
    private Integer nPermisoId;

    @Column("cRecurso")
    private String cRecurso;

    @Column("cAccion")
    private String cAccion;

    @Column("cDescripcionPermiso")
    private String cDescripcionPermiso;

    @Column("dFechaCreacion")
    private LocalDateTime dFechaCreacion;

    @Column("bEstado")
    private Boolean bEstado;

    public Permiso(String cRecurso, String cAccion, String cDescripcionPermiso) {
        this.cRecurso = cRecurso;
        this.cAccion = cAccion;
        this.cDescripcionPermiso = cDescripcionPermiso;
        this.dFechaCreacion = LocalDateTime.now();
        this.bEstado = true;
    }
}
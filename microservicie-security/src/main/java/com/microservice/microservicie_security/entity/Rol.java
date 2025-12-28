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
@Table("Rol")
public class Rol {

    @Id
    @Column("nRolId")
    private Integer nRolId;

    @Column("cNombreRol")
    private String cNombreRol;

    @Column("cDescripcionRol")
    private String cDescripcionRol;

    @Column("dFechaCreacion")
    private LocalDateTime dFechaCreacion;

    @Column("dFechaModificacion")
    private LocalDateTime dFechaModificacion;

    @Column("bEstado")
    private Boolean bEstado;

    public Rol(String cNombreRol, String cDescripcionRol) {
        this.cNombreRol = cNombreRol;
        this.cDescripcionRol = cDescripcionRol;
        this.dFechaCreacion = LocalDateTime.now();
        this.dFechaModificacion = LocalDateTime.now();
        this.bEstado = true;
    }
}
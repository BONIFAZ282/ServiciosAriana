package com.microservice.microservicie_security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionCheckResponse {

    private Boolean hasPermission;
    private String message;
    private String recurso;
    private String accion;
    private Integer usuarioId;

    public PermissionCheckResponse(Boolean hasPermission, String recurso, String accion, Integer usuarioId) {
        this.hasPermission = hasPermission;
        this.recurso = recurso;
        this.accion = accion;
        this.usuarioId = usuarioId;
        this.message = hasPermission ? "Permiso concedido" : "Permiso denegado";
    }
}
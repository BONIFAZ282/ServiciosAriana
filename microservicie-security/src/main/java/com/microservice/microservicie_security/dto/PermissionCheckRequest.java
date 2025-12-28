package com.microservice.microservicie_security.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionCheckRequest {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer usuarioId;

    @NotBlank(message = "El recurso es obligatorio")
    private String recurso;

    @NotBlank(message = "La acción es obligatoria")
    private String accion;
}
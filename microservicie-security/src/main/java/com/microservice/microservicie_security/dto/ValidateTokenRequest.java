package com.microservice.microservicie_security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenRequest {

    @NotBlank(message = "El token es obligatorio")
    private String token;
}
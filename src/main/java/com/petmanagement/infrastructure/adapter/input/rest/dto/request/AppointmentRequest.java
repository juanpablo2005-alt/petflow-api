package com.petmanagement.infrastructure.adapter.input.rest.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {

    @NotBlank(message = "El ID de la mascota es obligatorio")
    private String petId;

    @NotBlank(message = "El ID del usuario es obligatorio")
    private String userId;

    @NotBlank(message = "El veterinario es obligatorio")
    private String veterinarian;

    @NotNull(message = "La fecha de la cita es obligatoria")
    @Future(message = "La cita debe ser en el futuro")
    private LocalDateTime scheduledAt;

    private String reason;
}
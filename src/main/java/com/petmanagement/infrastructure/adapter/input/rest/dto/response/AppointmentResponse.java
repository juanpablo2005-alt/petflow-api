package com.petmanagement.infrastructure.adapter.input.rest.dto.response;

import com.petmanagement.domain.model.Appointment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponse {
    private String id;
    private String petId;
    private String userId;
    private String veterinarian;
    private LocalDateTime scheduledAt;
    private String reason;
    private Appointment.AppointmentStatus status;
    private LocalDateTime createdAt;
}
package com.petmanagement.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    private String id;
    private String petId;
    private String userId;
    private String veterinarian;
    private LocalDateTime scheduledAt;
    private String reason;
    private AppointmentStatus status;
    private LocalDateTime createdAt;

    public enum AppointmentStatus {
        PENDING,
        CONFIRMED,
        CANCELLED
    }
}
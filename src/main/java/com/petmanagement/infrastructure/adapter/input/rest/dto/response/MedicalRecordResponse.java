package com.petmanagement.infrastructure.adapter.input.rest.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MedicalRecordResponse {
    private String id;
    private String petId;
    private String description;
    private LocalDate consultationDate;
    private String veterinarian;
    private String diagnosis;
    private String treatment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

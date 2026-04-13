package com.petmanagement.infrastructure.adapter.input.rest.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VaccineResponse {
    private String id;
    private String petId;
    private String name;
    private LocalDate applicationDate;
    private LocalDate nextDoseDate;
    private String veterinarian;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

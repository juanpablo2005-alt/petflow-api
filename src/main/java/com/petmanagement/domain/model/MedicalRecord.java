package com.petmanagement.domain.model;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class MedicalRecord {
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

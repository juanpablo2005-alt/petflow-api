package com.petmanagement.domain.model;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Vaccine {
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

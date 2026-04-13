package com.petmanagement.infrastructure.adapter.output.persistence.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Document(collection = "vaccines")
public class VaccineDocument {
    @Id private String id;
    @Indexed private String petId;
    private String name;
    private LocalDate applicationDate;
    private LocalDate nextDoseDate;
    private String veterinarian;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.petmanagement.infrastructure.adapter.output.persistence.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Document(collection = "medical_records")
public class MedicalRecordDocument {
    @Id private String id;
    @Indexed private String petId;
    private String description;
    private LocalDate consultationDate;
    private String veterinarian;
    private String diagnosis;
    private String treatment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

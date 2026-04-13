package com.petmanagement.infrastructure.adapter.input.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MedicalRecordRequest {
    @NotBlank private String petId;
    @NotBlank private String description;
    @NotNull  private LocalDate consultationDate;
    @NotBlank private String veterinarian;
    private String diagnosis;
    private String treatment;
}

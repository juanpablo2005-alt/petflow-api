package com.petmanagement.infrastructure.adapter.input.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class VaccineRequest {
    @NotBlank private String petId;
    @NotBlank private String name;
    @NotNull  private LocalDate applicationDate;
    private LocalDate nextDoseDate;
    @NotBlank private String veterinarian;
    private String notes;
}

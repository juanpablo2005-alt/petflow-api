package com.petmanagement.infrastructure.adapter.input.rest.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PetRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @NotBlank(message = "La raza es obligatoria")
    private String breed;
    @NotNull @Min(0)
    private Integer age;
    private String photoUrl;
    @NotBlank(message = "La categoría es obligatoria")
    private String categoryId;
    @NotBlank(message = "El usuario es obligatorio")
    private String userId;
}

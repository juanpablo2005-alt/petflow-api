package com.petmanagement.infrastructure.adapter.input.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    private String description;
}

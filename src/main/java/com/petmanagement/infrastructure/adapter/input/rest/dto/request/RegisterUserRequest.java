package com.petmanagement.infrastructure.adapter.input.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @NotBlank @Email(message = "Email inválido")
    private String email;
    @NotBlank @Size(min = 6, message = "Mínimo 6 caracteres")
    private String password;
    private String phone;
}

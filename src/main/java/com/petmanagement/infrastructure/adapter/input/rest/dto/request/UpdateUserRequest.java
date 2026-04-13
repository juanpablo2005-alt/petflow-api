package com.petmanagement.infrastructure.adapter.input.rest.dto.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String phone;
}

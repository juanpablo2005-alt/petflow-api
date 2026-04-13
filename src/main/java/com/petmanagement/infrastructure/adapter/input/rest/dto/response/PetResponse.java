package com.petmanagement.infrastructure.adapter.input.rest.dto.response;

import com.petmanagement.domain.model.Pet;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PetResponse {
    private String id;
    private String name;
    private String breed;
    private Integer age;
    private String photoUrl;
    private String categoryId;
    private String userId;
    private Pet.PetStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.petmanagement.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Pet {
    private String id;
    private String name;
    private String breed;
    private Integer age;
    private String photoUrl;
    private String categoryId;
    private String userId;
    private PetStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum PetStatus { ACTIVE, INACTIVE }
}

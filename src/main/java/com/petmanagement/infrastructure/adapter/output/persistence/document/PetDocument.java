package com.petmanagement.infrastructure.adapter.output.persistence.document;

import com.petmanagement.domain.model.Pet;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Document(collection = "pets")
public class PetDocument {
    @Id private String id;
    private String name;
    private String breed;
    private Integer age;
    private String photoUrl;
    @Indexed private String categoryId;
    @Indexed private String userId;
    private Pet.PetStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

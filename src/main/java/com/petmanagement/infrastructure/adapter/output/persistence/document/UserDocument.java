package com.petmanagement.infrastructure.adapter.output.persistence.document;

import com.petmanagement.domain.model.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Document(collection = "users")
public class UserDocument {
    @Id private String id;
    private String name;
    @Indexed(unique = true) private String email;
    private String password;
    private String phone;
    private User.UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.petmanagement.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum UserStatus { ACTIVE, INACTIVE }

    public boolean isActive() { return UserStatus.ACTIVE.equals(this.status); }
    public void deactivate()  { this.status = UserStatus.INACTIVE; }
}

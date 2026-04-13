package com.petmanagement.infrastructure.adapter.input.rest.dto.response;

import com.petmanagement.domain.model.User;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private String phone;
    private User.UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

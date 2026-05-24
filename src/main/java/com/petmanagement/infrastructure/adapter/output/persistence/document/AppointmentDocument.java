package com.petmanagement.infrastructure.adapter.output.persistence.document;

import com.petmanagement.domain.model.Appointment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "appointments")
public class AppointmentDocument {

    @Id
    private String id;

    @Indexed
    private String petId;

    @Indexed
    private String userId;

    private String veterinarian;
    private LocalDateTime scheduledAt;
    private String reason;
    private Appointment.AppointmentStatus status;
    private LocalDateTime createdAt;
}
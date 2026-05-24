package com.petmanagement.infrastructure.adapter.output.persistence.adapter;

import com.petmanagement.application.port.output.AppointmentRepositoryPort;
import com.petmanagement.domain.model.Appointment;
import com.petmanagement.infrastructure.adapter.output.persistence.document.AppointmentDocument;
import com.petmanagement.infrastructure.adapter.output.persistence.repository.AppointmentReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AppointmentRepositoryAdapter implements AppointmentRepositoryPort {

    private final AppointmentReactiveRepository repository;

    @Override
    public Mono<Appointment> save(Appointment appointment) {
        return repository.save(toDocument(appointment)).map(this::toDomain);
    }

    @Override
    public Flux<Appointment> findByUserId(String userId) {
        return repository.findByUserId(userId).map(this::toDomain);
    }

    @Override
    public Flux<Appointment> findByPetId(String petId) {
        return repository.findByPetId(petId).map(this::toDomain);
    }

    @Override
    public Mono<Appointment> findById(String id) {
        return repository.findById(id).map(this::toDomain);
    }

    private AppointmentDocument toDocument(Appointment a) {
        return AppointmentDocument.builder()
                .id(a.getId())
                .petId(a.getPetId())
                .userId(a.getUserId())
                .veterinarian(a.getVeterinarian())
                .scheduledAt(a.getScheduledAt())
                .reason(a.getReason())
                .status(a.getStatus())
                .createdAt(a.getCreatedAt())
                .build();
    }

    private Appointment toDomain(AppointmentDocument d) {
        return Appointment.builder()
                .id(d.getId())
                .petId(d.getPetId())
                .userId(d.getUserId())
                .veterinarian(d.getVeterinarian())
                .scheduledAt(d.getScheduledAt())
                .reason(d.getReason())
                .status(d.getStatus())
                .createdAt(d.getCreatedAt())
                .build();
    }
}
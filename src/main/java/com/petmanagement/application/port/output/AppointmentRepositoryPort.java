package com.petmanagement.application.port.output;

import com.petmanagement.domain.model.Appointment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AppointmentRepositoryPort {
    Mono<Appointment> save(Appointment appointment);
    Flux<Appointment> findByUserId(String userId);
    Flux<Appointment> findByPetId(String petId);
    Mono<Appointment> findById(String id);
}
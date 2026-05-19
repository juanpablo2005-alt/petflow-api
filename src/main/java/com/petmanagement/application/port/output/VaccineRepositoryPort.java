package com.petmanagement.application.port.output;

import com.petmanagement.domain.model.Vaccine;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface VaccineRepositoryPort {
    Mono<Vaccine> save(Vaccine vaccine);
    Flux<Vaccine> findByPetId(String petId);
    Mono<Vaccine> findById(String id);
    Mono<Void> deleteById(String id);
    Flux<Vaccine> findByNextDoseDateBetween(LocalDate from, LocalDate to);
}
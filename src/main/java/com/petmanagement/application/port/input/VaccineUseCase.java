package com.petmanagement.application.port.input;

import com.petmanagement.domain.model.Vaccine;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VaccineUseCase {
    Mono<Vaccine> create(Vaccine vaccine);
    Flux<Vaccine> findByPetId(String petId);
    Mono<Vaccine> findById(String id);
    Mono<Vaccine> update(String id, Vaccine vaccine);
    Mono<Void> delete(String id);
}

package com.petmanagement.application.service;

import com.petmanagement.application.port.input.VaccineUseCase;
import com.petmanagement.application.port.output.PetRepositoryPort;
import com.petmanagement.application.port.output.VaccineRepositoryPort;
import com.petmanagement.domain.exception.PetNotFoundException;
import com.petmanagement.domain.exception.VaccineNotFoundException;
import com.petmanagement.domain.model.Vaccine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaccineService implements VaccineUseCase {

    private final VaccineRepositoryPort vaccineRepositoryPort;
    private final PetRepositoryPort petRepositoryPort;

    @Override
    public Mono<Vaccine> create(Vaccine vaccine) {
        return petRepositoryPort.findById(vaccine.getPetId())
                .switchIfEmpty(Mono.error(new PetNotFoundException(vaccine.getPetId())))
                .then(Mono.defer(() -> {
                    vaccine.setCreatedAt(LocalDateTime.now());
                    vaccine.setUpdatedAt(LocalDateTime.now());
                    return vaccineRepositoryPort.save(vaccine);
                }));
    }

    @Override
    public Flux<Vaccine> findByPetId(String petId) {
        return petRepositoryPort.findById(petId)
                .switchIfEmpty(Mono.error(new PetNotFoundException(petId)))
                .thenMany(vaccineRepositoryPort.findByPetId(petId));
    }

    @Override
    public Mono<Vaccine> findById(String id) {
        return vaccineRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new VaccineNotFoundException(id)));
    }

    @Override
    public Mono<Vaccine> update(String id, Vaccine updated) {
        return vaccineRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new VaccineNotFoundException(id)))
                .flatMap(existing -> {
                    existing.setName(updated.getName());
                    existing.setApplicationDate(updated.getApplicationDate());
                    existing.setNextDoseDate(updated.getNextDoseDate());
                    existing.setVeterinarian(updated.getVeterinarian());
                    existing.setNotes(updated.getNotes());
                    existing.setUpdatedAt(LocalDateTime.now());
                    return vaccineRepositoryPort.save(existing);
                });
    }

    @Override
    public Mono<Void> delete(String id) {
        return vaccineRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new VaccineNotFoundException(id)))
                .flatMap(v -> vaccineRepositoryPort.deleteById(id));
    }
}

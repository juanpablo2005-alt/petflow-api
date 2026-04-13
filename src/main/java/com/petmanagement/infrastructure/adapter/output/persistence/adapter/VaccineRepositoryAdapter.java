package com.petmanagement.infrastructure.adapter.output.persistence.adapter;

import com.petmanagement.application.port.output.VaccineRepositoryPort;
import com.petmanagement.domain.model.Vaccine;
import com.petmanagement.infrastructure.adapter.output.persistence.document.VaccineDocument;
import com.petmanagement.infrastructure.adapter.output.persistence.repository.VaccineReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class VaccineRepositoryAdapter implements VaccineRepositoryPort {

    private final VaccineReactiveRepository repository;

    @Override
    public Mono<Vaccine> save(Vaccine vaccine) {
        return repository.save(toDocument(vaccine)).map(this::toDomain);
    }

    @Override
    public Flux<Vaccine> findByPetId(String petId) {
        return repository.findByPetId(petId).map(this::toDomain);
    }

    @Override
    public Mono<Vaccine> findById(String id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }

    private VaccineDocument toDocument(Vaccine v) {
        return VaccineDocument.builder()
                .id(v.getId()).petId(v.getPetId()).name(v.getName())
                .applicationDate(v.getApplicationDate()).nextDoseDate(v.getNextDoseDate())
                .veterinarian(v.getVeterinarian()).notes(v.getNotes())
                .createdAt(v.getCreatedAt()).updatedAt(v.getUpdatedAt())
                .build();
    }

    private Vaccine toDomain(VaccineDocument d) {
        return Vaccine.builder()
                .id(d.getId()).petId(d.getPetId()).name(d.getName())
                .applicationDate(d.getApplicationDate()).nextDoseDate(d.getNextDoseDate())
                .veterinarian(d.getVeterinarian()).notes(d.getNotes())
                .createdAt(d.getCreatedAt()).updatedAt(d.getUpdatedAt())
                .build();
    }
}

package com.petmanagement.infrastructure.adapter.output.persistence.adapter;

import com.petmanagement.application.port.output.VaccineRepositoryPort;
import com.petmanagement.domain.model.Vaccine;
import com.petmanagement.infrastructure.adapter.output.persistence.document.VaccineDocument;
import com.petmanagement.infrastructure.adapter.output.persistence.repository.VaccineReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Override
    public Flux<Vaccine> findByNextDoseDateBetween(LocalDate from, LocalDate to) {
        return repository.findByNextDoseDateBetween(from, to).map(this::toDomain);
    }

    private VaccineDocument toDocument(Vaccine v) {
        VaccineDocument doc = new VaccineDocument();
        doc.setId(v.getId());
        doc.setPetId(v.getPetId());
        doc.setName(v.getName());
        doc.setApplicationDate(v.getApplicationDate());
        doc.setNextDoseDate(v.getNextDoseDate());
        doc.setVeterinarian(v.getVeterinarian());
        doc.setNotes(v.getNotes());
        doc.setCreatedAt(v.getCreatedAt() != null ? v.getCreatedAt() : LocalDateTime.now());
        doc.setUpdatedAt(v.getUpdatedAt() != null ? v.getUpdatedAt() : LocalDateTime.now());
        return doc;
    }

    private Vaccine toDomain(VaccineDocument doc) {
        return Vaccine.builder()
                .id(doc.getId())
                .petId(doc.getPetId())
                .name(doc.getName())
                .applicationDate(doc.getApplicationDate())
                .nextDoseDate(doc.getNextDoseDate())
                .veterinarian(doc.getVeterinarian())
                .notes(doc.getNotes())
                .createdAt(doc.getCreatedAt())
                .updatedAt(doc.getUpdatedAt())
                .build();
    }
}
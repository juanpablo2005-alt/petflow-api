package com.petmanagement.infrastructure.adapter.output.persistence.repository;

import com.petmanagement.infrastructure.adapter.output.persistence.document.VaccineDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface VaccineReactiveRepository extends ReactiveMongoRepository<VaccineDocument, String> {
    Flux<VaccineDocument> findByPetId(String petId);
    Flux<VaccineDocument> findByNextDoseDateBetween(LocalDate from, LocalDate to);
}
package com.petmanagement.infrastructure.adapter.output.persistence.repository;

import com.petmanagement.infrastructure.adapter.output.persistence.document.VaccineDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface VaccineReactiveRepository extends ReactiveMongoRepository<VaccineDocument, String> {
    Flux<VaccineDocument> findByPetId(String petId);
}

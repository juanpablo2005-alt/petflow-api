package com.petmanagement.infrastructure.adapter.output.persistence.repository;

import com.petmanagement.domain.model.Pet;
import com.petmanagement.infrastructure.adapter.output.persistence.document.PetDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PetReactiveRepository extends ReactiveMongoRepository<PetDocument, String> {
    Flux<PetDocument> findByUserId(String userId);
    Flux<PetDocument> findByCategoryId(String categoryId);
    Flux<PetDocument> findByStatus(Pet.PetStatus status);
}
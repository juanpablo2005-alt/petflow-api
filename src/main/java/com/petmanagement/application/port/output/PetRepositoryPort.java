package com.petmanagement.application.port.output;

import com.petmanagement.domain.model.Pet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PetRepositoryPort {
    Mono<Pet> save(Pet pet);
    Flux<Pet> findAll();
    Mono<Pet> findById(String id);
    Flux<Pet> findByUserId(String userId);
    Flux<Pet> findByCategoryId(String categoryId);
    Mono<Void> deleteById(String id);
}

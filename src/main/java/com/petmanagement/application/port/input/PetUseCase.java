package com.petmanagement.application.port.input;

import com.petmanagement.domain.model.Pet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PetUseCase {
    Mono<Pet> create(Pet pet);
    Flux<Pet> findAll();
    Mono<Pet> findById(String id);
    Flux<Pet> findByUserId(String userId);
    Flux<Pet> findByCategoryId(String categoryId);
    Mono<Pet> update(String id, Pet pet);
    Mono<Void> delete(String id);
}

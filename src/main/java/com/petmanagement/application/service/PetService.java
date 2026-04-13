package com.petmanagement.application.service;

import com.petmanagement.application.port.input.PetUseCase;
import com.petmanagement.application.port.output.CategoryRepositoryPort;
import com.petmanagement.application.port.output.PetRepositoryPort;
import com.petmanagement.application.port.output.UserRepositoryPort;
import com.petmanagement.domain.exception.CategoryNotFoundException;
import com.petmanagement.domain.exception.PetNotFoundException;
import com.petmanagement.domain.exception.UserNotFoundException;
import com.petmanagement.domain.model.Pet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService implements PetUseCase {

    private final PetRepositoryPort petRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public Mono<Pet> create(Pet pet) {
        return userRepositoryPort.findById(pet.getUserId())
                .switchIfEmpty(Mono.error(new UserNotFoundException(pet.getUserId())))
                .then(categoryRepositoryPort.findById(pet.getCategoryId())
                        .switchIfEmpty(Mono.error(new CategoryNotFoundException(pet.getCategoryId()))))
                .then(Mono.defer(() -> {
                    pet.setStatus(Pet.PetStatus.ACTIVE);
                    pet.setCreatedAt(LocalDateTime.now());
                    pet.setUpdatedAt(LocalDateTime.now());
                    return petRepositoryPort.save(pet);
                }));
    }

    @Override
    public Flux<Pet> findAll() { return petRepositoryPort.findAll(); }

    @Override
    public Mono<Pet> findById(String id) {
        return petRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new PetNotFoundException(id)));
    }

    @Override
    public Flux<Pet> findByUserId(String userId) {
        return userRepositoryPort.findById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException(userId)))
                .thenMany(petRepositoryPort.findByUserId(userId));
    }

    @Override
    public Flux<Pet> findByCategoryId(String categoryId) {
        return categoryRepositoryPort.findById(categoryId)
                .switchIfEmpty(Mono.error(new CategoryNotFoundException(categoryId)))
                .thenMany(petRepositoryPort.findByCategoryId(categoryId));
    }

    @Override
    public Mono<Pet> update(String id, Pet updatedPet) {
        return petRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new PetNotFoundException(id)))
                .flatMap(existing -> {
                    existing.setName(updatedPet.getName());
                    existing.setBreed(updatedPet.getBreed());
                    existing.setAge(updatedPet.getAge());
                    existing.setPhotoUrl(updatedPet.getPhotoUrl());
                    if (updatedPet.getCategoryId() != null) existing.setCategoryId(updatedPet.getCategoryId());
                    existing.setUpdatedAt(LocalDateTime.now());
                    return petRepositoryPort.save(existing);
                });
    }

    @Override
    public Mono<Void> delete(String id) {
        return petRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new PetNotFoundException(id)))
                .flatMap(p -> petRepositoryPort.deleteById(id));
    }
}

package com.petmanagement.infrastructure.adapter.output.persistence.adapter;

import com.petmanagement.application.port.output.PetRepositoryPort;
import com.petmanagement.domain.model.Pet;
import com.petmanagement.infrastructure.adapter.output.persistence.document.PetDocument;
import com.petmanagement.infrastructure.adapter.output.persistence.repository.PetReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PetRepositoryAdapter implements PetRepositoryPort {

    private final PetReactiveRepository repository;

    @Override
    public Mono<Pet> save(Pet pet) {
        return repository.save(toDocument(pet)).map(this::toDomain);
    }

    @Override
    public Flux<Pet> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<Pet> findById(String id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<Pet> findByUserId(String userId) {
        return repository.findByUserId(userId).map(this::toDomain);
    }

    @Override
    public Flux<Pet> findByCategoryId(String categoryId) {
        return repository.findByCategoryId(categoryId).map(this::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }

    private PetDocument toDocument(Pet p) {
        return PetDocument.builder()
                .id(p.getId()).name(p.getName()).breed(p.getBreed()).age(p.getAge())
                .photoUrl(p.getPhotoUrl()).categoryId(p.getCategoryId()).userId(p.getUserId())
                .status(p.getStatus()).createdAt(p.getCreatedAt()).updatedAt(p.getUpdatedAt())
                .build();
    }

    private Pet toDomain(PetDocument d) {
        return Pet.builder()
                .id(d.getId()).name(d.getName()).breed(d.getBreed()).age(d.getAge())
                .photoUrl(d.getPhotoUrl()).categoryId(d.getCategoryId()).userId(d.getUserId())
                .status(d.getStatus()).createdAt(d.getCreatedAt()).updatedAt(d.getUpdatedAt())
                .build();
    }
}

package com.petmanagement.application.port.output;

import com.petmanagement.domain.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryRepositoryPort {
    Mono<Category> save(Category category);
    Flux<Category> findAll();
    Mono<Category> findById(String id);
    Mono<Boolean> existsByName(String name);
    Mono<Void> deleteById(String id);
}

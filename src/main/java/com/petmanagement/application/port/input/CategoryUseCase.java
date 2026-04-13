package com.petmanagement.application.port.input;

import com.petmanagement.domain.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryUseCase {
    Mono<Category> create(Category category);
    Flux<Category> findAll();
    Mono<Category> findById(String id);
    Mono<Category> update(String id, Category category);
    Mono<Void> delete(String id);
}

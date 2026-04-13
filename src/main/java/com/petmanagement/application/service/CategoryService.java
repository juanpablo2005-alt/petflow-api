package com.petmanagement.application.service;

import com.petmanagement.application.port.input.CategoryUseCase;
import com.petmanagement.application.port.output.CategoryRepositoryPort;
import com.petmanagement.domain.exception.CategoryNotFoundException;
import com.petmanagement.domain.exception.DomainException;
import com.petmanagement.domain.model.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryUseCase {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public Mono<Category> create(Category category) {
        return categoryRepositoryPort.existsByName(category.getName())
                .flatMap(exists -> {
                    if (exists) return Mono.error(new DomainException("Ya existe una categoría con el nombre: " + category.getName()));
                    category.setActive(true);
                    category.setCreatedAt(LocalDateTime.now());
                    category.setUpdatedAt(LocalDateTime.now());
                    return categoryRepositoryPort.save(category);
                });
    }

    @Override
    public Flux<Category> findAll() {
        return categoryRepositoryPort.findAll();
    }

    @Override
    public Mono<Category> findById(String id) {
        return categoryRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new CategoryNotFoundException(id)));
    }

    @Override
    public Mono<Category> update(String id, Category updatedCategory) {
        return categoryRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new CategoryNotFoundException(id)))
                .flatMap(existing -> {
                    existing.setName(updatedCategory.getName());
                    existing.setDescription(updatedCategory.getDescription());
                    existing.setUpdatedAt(LocalDateTime.now());
                    return categoryRepositoryPort.save(existing);
                });
    }

    @Override
    public Mono<Void> delete(String id) {
        return categoryRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new CategoryNotFoundException(id)))
                .flatMap(c -> categoryRepositoryPort.deleteById(id));
    }
}

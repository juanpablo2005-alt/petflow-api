package com.petmanagement.infrastructure.adapter.output.persistence.adapter;

import com.petmanagement.application.port.output.CategoryRepositoryPort;
import com.petmanagement.domain.model.Category;
import com.petmanagement.infrastructure.adapter.output.persistence.document.CategoryDocument;
import com.petmanagement.infrastructure.adapter.output.persistence.repository.CategoryReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {

    private final CategoryReactiveRepository repository;

    @Override
    public Mono<Category> save(Category category) {
        return repository.save(toDocument(category)).map(this::toDomain);
    }

    @Override
    public Flux<Category> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<Category> findById(String id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }

    private CategoryDocument toDocument(Category c) {
        return CategoryDocument.builder()
                .id(c.getId()).name(c.getName()).description(c.getDescription())
                .active(c.isActive()).createdAt(c.getCreatedAt()).updatedAt(c.getUpdatedAt())
                .build();
    }

    private Category toDomain(CategoryDocument d) {
        return Category.builder()
                .id(d.getId()).name(d.getName()).description(d.getDescription())
                .active(d.isActive()).createdAt(d.getCreatedAt()).updatedAt(d.getUpdatedAt())
                .build();
    }
}

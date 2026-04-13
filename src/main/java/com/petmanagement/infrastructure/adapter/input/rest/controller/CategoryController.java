package com.petmanagement.infrastructure.adapter.input.rest.controller;

import com.petmanagement.application.port.input.CategoryUseCase;
import com.petmanagement.domain.model.Category;
import com.petmanagement.infrastructure.adapter.input.rest.dto.request.CategoryRequest;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.ApiResponse;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "Gestión de categorías de mascotas")
public class CategoryController {

    private final CategoryUseCase categoryUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear categoría")
    public Mono<ApiResponse<CategoryResponse>> create(@Valid @RequestBody CategoryRequest req) {
        Category cat = Category.builder().name(req.getName()).description(req.getDescription()).build();
        return categoryUseCase.create(cat).map(c -> ApiResponse.ok("Categoría creada", toResponse(c)));
    }

    @GetMapping
    @Operation(summary = "Listar todas las categorías")
    public Flux<CategoryResponse> findAll() {
        return categoryUseCase.findAll().map(this::toResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID")
    public Mono<ApiResponse<CategoryResponse>> findById(@PathVariable String id) {
        return categoryUseCase.findById(id).map(c -> ApiResponse.ok(toResponse(c)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría")
    public Mono<ApiResponse<CategoryResponse>> update(@PathVariable String id,
                                                       @Valid @RequestBody CategoryRequest req) {
        Category cat = Category.builder().name(req.getName()).description(req.getDescription()).build();
        return categoryUseCase.update(id, cat).map(c -> ApiResponse.ok("Categoría actualizada", toResponse(c)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar categoría")
    public Mono<Void> delete(@PathVariable String id) {
        return categoryUseCase.delete(id);
    }

    private CategoryResponse toResponse(Category c) {
        CategoryResponse r = new CategoryResponse();
        r.setId(c.getId()); r.setName(c.getName()); r.setDescription(c.getDescription());
        r.setActive(c.isActive()); r.setCreatedAt(c.getCreatedAt()); r.setUpdatedAt(c.getUpdatedAt());
        return r;
    }
}

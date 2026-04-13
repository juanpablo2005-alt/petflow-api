package com.petmanagement.infrastructure.adapter.input.rest.controller;

import com.petmanagement.application.port.input.PetUseCase;
import com.petmanagement.domain.model.Pet;
import com.petmanagement.infrastructure.adapter.input.rest.dto.request.PetRequest;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.ApiResponse;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.PetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
@Tag(name = "Mascotas", description = "CRUD de mascotas")
public class PetController {

    private final PetUseCase petUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar mascota")
    public Mono<ApiResponse<PetResponse>> create(@Valid @RequestBody PetRequest req) {
        Pet pet = toDomain(req);
        return petUseCase.create(pet).map(p -> ApiResponse.ok("Mascota registrada", toResponse(p)));
    }

    @GetMapping
    @Operation(summary = "Listar todas las mascotas")
    public Flux<PetResponse> findAll() {
        return petUseCase.findAll().map(this::toResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener mascota por ID")
    public Mono<ApiResponse<PetResponse>> findById(@PathVariable String id) {
        return petUseCase.findById(id).map(p -> ApiResponse.ok(toResponse(p)));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Mascotas por usuario")
    public Flux<PetResponse> findByUserId(@PathVariable String userId) {
        return petUseCase.findByUserId(userId).map(this::toResponse);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Mascotas por categoría")
    public Flux<PetResponse> findByCategoryId(@PathVariable String categoryId) {
        return petUseCase.findByCategoryId(categoryId).map(this::toResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar mascota")
    public Mono<ApiResponse<PetResponse>> update(@PathVariable String id,
                                                  @Valid @RequestBody PetRequest req) {
        return petUseCase.update(id, toDomain(req)).map(p -> ApiResponse.ok("Mascota actualizada", toResponse(p)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar mascota")
    public Mono<Void> delete(@PathVariable String id) {
        return petUseCase.delete(id);
    }

    private Pet toDomain(PetRequest req) {
        return Pet.builder().name(req.getName()).breed(req.getBreed()).age(req.getAge())
                .photoUrl(req.getPhotoUrl()).categoryId(req.getCategoryId()).userId(req.getUserId()).build();
    }

    private PetResponse toResponse(Pet p) {
        PetResponse r = new PetResponse();
        r.setId(p.getId()); r.setName(p.getName()); r.setBreed(p.getBreed()); r.setAge(p.getAge());
        r.setPhotoUrl(p.getPhotoUrl()); r.setCategoryId(p.getCategoryId()); r.setUserId(p.getUserId());
        r.setStatus(p.getStatus()); r.setCreatedAt(p.getCreatedAt()); r.setUpdatedAt(p.getUpdatedAt());
        return r;
    }
}

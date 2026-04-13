package com.petmanagement.infrastructure.adapter.input.rest.controller;

import com.petmanagement.application.port.input.VaccineUseCase;
import com.petmanagement.domain.model.Vaccine;
import com.petmanagement.infrastructure.adapter.input.rest.dto.request.VaccineRequest;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.ApiResponse;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.VaccineResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/vaccines")
@RequiredArgsConstructor
@Tag(name = "Vacunas", description = "Gestión de vacunas de mascotas")
public class VaccineController {

    private final VaccineUseCase vaccineUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar vacuna")
    public Mono<ApiResponse<VaccineResponse>> create(@Valid @RequestBody VaccineRequest req) {
        return vaccineUseCase.create(toDomain(req)).map(v -> ApiResponse.ok("Vacuna registrada", toResponse(v)));
    }

    @GetMapping("/pet/{petId}")
    @Operation(summary = "Vacunas de una mascota")
    public Flux<VaccineResponse> findByPetId(@PathVariable String petId) {
        return vaccineUseCase.findByPetId(petId).map(this::toResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener vacuna por ID")
    public Mono<ApiResponse<VaccineResponse>> findById(@PathVariable String id) {
        return vaccineUseCase.findById(id).map(v -> ApiResponse.ok(toResponse(v)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar vacuna")
    public Mono<ApiResponse<VaccineResponse>> update(@PathVariable String id,
                                                      @Valid @RequestBody VaccineRequest req) {
        return vaccineUseCase.update(id, toDomain(req)).map(v -> ApiResponse.ok("Vacuna actualizada", toResponse(v)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar vacuna")
    public Mono<Void> delete(@PathVariable String id) {
        return vaccineUseCase.delete(id);
    }

    private Vaccine toDomain(VaccineRequest req) {
        return Vaccine.builder().petId(req.getPetId()).name(req.getName())
                .applicationDate(req.getApplicationDate()).nextDoseDate(req.getNextDoseDate())
                .veterinarian(req.getVeterinarian()).notes(req.getNotes()).build();
    }

    private VaccineResponse toResponse(Vaccine v) {
        VaccineResponse r = new VaccineResponse();
        r.setId(v.getId()); r.setPetId(v.getPetId()); r.setName(v.getName());
        r.setApplicationDate(v.getApplicationDate()); r.setNextDoseDate(v.getNextDoseDate());
        r.setVeterinarian(v.getVeterinarian()); r.setNotes(v.getNotes());
        r.setCreatedAt(v.getCreatedAt()); r.setUpdatedAt(v.getUpdatedAt());
        return r;
    }
}

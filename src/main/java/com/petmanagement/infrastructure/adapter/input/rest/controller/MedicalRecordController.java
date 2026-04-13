package com.petmanagement.infrastructure.adapter.input.rest.controller;

import com.petmanagement.application.port.input.MedicalRecordUseCase;
import com.petmanagement.domain.model.MedicalRecord;
import com.petmanagement.infrastructure.adapter.input.rest.dto.request.MedicalRecordRequest;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.ApiResponse;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.MedicalRecordResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/medical-records")
@RequiredArgsConstructor
@Tag(name = "Historial Médico", description = "Historial médico de mascotas")
public class MedicalRecordController {

    private final MedicalRecordUseCase medicalRecordUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar consulta médica")
    public Mono<ApiResponse<MedicalRecordResponse>> create(@Valid @RequestBody MedicalRecordRequest req) {
        return medicalRecordUseCase.create(toDomain(req))
                .map(r -> ApiResponse.ok("Consulta registrada", toResponse(r)));
    }

    @GetMapping("/pet/{petId}")
    @Operation(summary = "Historial médico de una mascota")
    public Flux<MedicalRecordResponse> findByPetId(@PathVariable String petId) {
        return medicalRecordUseCase.findByPetId(petId).map(this::toResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener registro médico por ID")
    public Mono<ApiResponse<MedicalRecordResponse>> findById(@PathVariable String id) {
        return medicalRecordUseCase.findById(id).map(r -> ApiResponse.ok(toResponse(r)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar registro médico")
    public Mono<ApiResponse<MedicalRecordResponse>> update(@PathVariable String id,
                                                            @Valid @RequestBody MedicalRecordRequest req) {
        return medicalRecordUseCase.update(id, toDomain(req))
                .map(r -> ApiResponse.ok("Registro actualizado", toResponse(r)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar registro médico")
    public Mono<Void> delete(@PathVariable String id) {
        return medicalRecordUseCase.delete(id);
    }

    private MedicalRecord toDomain(MedicalRecordRequest req) {
        return MedicalRecord.builder().petId(req.getPetId()).description(req.getDescription())
                .consultationDate(req.getConsultationDate()).veterinarian(req.getVeterinarian())
                .diagnosis(req.getDiagnosis()).treatment(req.getTreatment()).build();
    }

    private MedicalRecordResponse toResponse(MedicalRecord r) {
        MedicalRecordResponse res = new MedicalRecordResponse();
        res.setId(r.getId()); res.setPetId(r.getPetId()); res.setDescription(r.getDescription());
        res.setConsultationDate(r.getConsultationDate()); res.setVeterinarian(r.getVeterinarian());
        res.setDiagnosis(r.getDiagnosis()); res.setTreatment(r.getTreatment());
        res.setCreatedAt(r.getCreatedAt()); res.setUpdatedAt(r.getUpdatedAt());
        return res;
    }
}

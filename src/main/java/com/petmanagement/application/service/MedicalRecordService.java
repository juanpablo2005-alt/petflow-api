package com.petmanagement.application.service;

import com.petmanagement.application.port.input.MedicalRecordUseCase;
import com.petmanagement.application.port.output.MedicalRecordRepositoryPort;
import com.petmanagement.application.port.output.PetRepositoryPort;
import com.petmanagement.domain.exception.MedicalRecordNotFoundException;
import com.petmanagement.domain.exception.PetNotFoundException;
import com.petmanagement.domain.model.MedicalRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicalRecordService implements MedicalRecordUseCase {

    private final MedicalRecordRepositoryPort medicalRecordRepositoryPort;
    private final PetRepositoryPort petRepositoryPort;

    @Override
    public Mono<MedicalRecord> create(MedicalRecord record) {
        return petRepositoryPort.findById(record.getPetId())
                .switchIfEmpty(Mono.error(new PetNotFoundException(record.getPetId())))
                .then(Mono.defer(() -> {
                    record.setCreatedAt(LocalDateTime.now());
                    record.setUpdatedAt(LocalDateTime.now());
                    return medicalRecordRepositoryPort.save(record);
                }));
    }

    @Override
    public Flux<MedicalRecord> findByPetId(String petId) {
        return petRepositoryPort.findById(petId)
                .switchIfEmpty(Mono.error(new PetNotFoundException(petId)))
                .thenMany(medicalRecordRepositoryPort.findByPetId(petId));
    }

    @Override
    public Mono<MedicalRecord> findById(String id) {
        return medicalRecordRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new MedicalRecordNotFoundException(id)));
    }

    @Override
    public Mono<MedicalRecord> update(String id, MedicalRecord updated) {
        return medicalRecordRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new MedicalRecordNotFoundException(id)))
                .flatMap(existing -> {
                    existing.setDescription(updated.getDescription());
                    existing.setConsultationDate(updated.getConsultationDate());
                    existing.setVeterinarian(updated.getVeterinarian());
                    existing.setDiagnosis(updated.getDiagnosis());
                    existing.setTreatment(updated.getTreatment());
                    existing.setUpdatedAt(LocalDateTime.now());
                    return medicalRecordRepositoryPort.save(existing);
                });
    }

    @Override
    public Mono<Void> delete(String id) {
        return medicalRecordRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new MedicalRecordNotFoundException(id)))
                .flatMap(r -> medicalRecordRepositoryPort.deleteById(id));
    }
}

package com.petmanagement.application.port.input;

import com.petmanagement.domain.model.MedicalRecord;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MedicalRecordUseCase {
    Mono<MedicalRecord> create(MedicalRecord record);
    Flux<MedicalRecord> findByPetId(String petId);
    Mono<MedicalRecord> findById(String id);
    Mono<MedicalRecord> update(String id, MedicalRecord record);
    Mono<Void> delete(String id);
}

package com.petmanagement.application.port.output;

import com.petmanagement.domain.model.MedicalRecord;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MedicalRecordRepositoryPort {
    Mono<MedicalRecord> save(MedicalRecord record);
    Flux<MedicalRecord> findByPetId(String petId);
    Mono<MedicalRecord> findById(String id);
    Mono<Void> deleteById(String id);
}

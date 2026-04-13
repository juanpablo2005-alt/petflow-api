package com.petmanagement.infrastructure.adapter.output.persistence.repository;

import com.petmanagement.infrastructure.adapter.output.persistence.document.MedicalRecordDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MedicalRecordReactiveRepository extends ReactiveMongoRepository<MedicalRecordDocument, String> {
    Flux<MedicalRecordDocument> findByPetId(String petId);
}

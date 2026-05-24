package com.petmanagement.infrastructure.adapter.output.persistence.repository;

import com.petmanagement.infrastructure.adapter.output.persistence.document.AppointmentDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AppointmentReactiveRepository
        extends ReactiveMongoRepository<AppointmentDocument, String> {

    Flux<AppointmentDocument> findByUserId(String userId);
    Flux<AppointmentDocument> findByPetId(String petId);
}
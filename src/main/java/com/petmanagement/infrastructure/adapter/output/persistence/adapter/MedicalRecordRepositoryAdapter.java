package com.petmanagement.infrastructure.adapter.output.persistence.adapter;

import com.petmanagement.application.port.output.MedicalRecordRepositoryPort;
import com.petmanagement.domain.model.MedicalRecord;
import com.petmanagement.infrastructure.adapter.output.persistence.document.MedicalRecordDocument;
import com.petmanagement.infrastructure.adapter.output.persistence.repository.MedicalRecordReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MedicalRecordRepositoryAdapter implements MedicalRecordRepositoryPort {

    private final MedicalRecordReactiveRepository repository;

    @Override
    public Mono<MedicalRecord> save(MedicalRecord record) {
        return repository.save(toDocument(record)).map(this::toDomain);
    }

    @Override
    public Flux<MedicalRecord> findByPetId(String petId) {
        return repository.findByPetId(petId).map(this::toDomain);
    }

    @Override
    public Mono<MedicalRecord> findById(String id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }

    private MedicalRecordDocument toDocument(MedicalRecord r) {
        return MedicalRecordDocument.builder()
                .id(r.getId()).petId(r.getPetId()).description(r.getDescription())
                .consultationDate(r.getConsultationDate()).veterinarian(r.getVeterinarian())
                .diagnosis(r.getDiagnosis()).treatment(r.getTreatment())
                .createdAt(r.getCreatedAt()).updatedAt(r.getUpdatedAt())
                .build();
    }

    private MedicalRecord toDomain(MedicalRecordDocument d) {
        return MedicalRecord.builder()
                .id(d.getId()).petId(d.getPetId()).description(d.getDescription())
                .consultationDate(d.getConsultationDate()).veterinarian(d.getVeterinarian())
                .diagnosis(d.getDiagnosis()).treatment(d.getTreatment())
                .createdAt(d.getCreatedAt()).updatedAt(d.getUpdatedAt())
                .build();
    }
}

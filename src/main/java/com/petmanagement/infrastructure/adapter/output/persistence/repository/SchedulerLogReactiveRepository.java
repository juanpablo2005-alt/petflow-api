package com.petmanagement.infrastructure.adapter.output.persistence.repository;

import com.petmanagement.infrastructure.adapter.output.persistence.document.SchedulerLogDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface SchedulerLogReactiveRepository extends ReactiveMongoRepository<SchedulerLogDocument, String> {
    Flux<SchedulerLogDocument> findByJobNameOrderByExecutedAtDesc(String jobName);
}
package com.petmanagement.infrastructure.adapter.output.persistence.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "scheduler_logs")
public class SchedulerLogDocument {

    @Id
    private String id;

    private String jobName;         // nombre del cron que se ejecutó
    private LocalDateTime executedAt;
    private int recordsProcessed;   // cuántos registros afectó
    private String status;          // SUCCESS / ERROR
    private String message;         // detalle adicional
}
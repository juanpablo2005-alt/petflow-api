package com.petmanagement.infrastructure.scheduler;

import com.petmanagement.application.port.output.MedicalRecordRepositoryPort;
import com.petmanagement.application.port.output.PetRepositoryPort;
import com.petmanagement.domain.model.Pet;
import com.petmanagement.infrastructure.adapter.output.persistence.document.SchedulerLogDocument;
import com.petmanagement.infrastructure.adapter.output.persistence.repository.SchedulerLogReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class PetAuditScheduler {

    private final PetRepositoryPort petRepositoryPort;
    private final MedicalRecordRepositoryPort medicalRecordRepositoryPort;
    private final SchedulerLogReactiveRepository logRepository;

    @Scheduled(cron = "${scheduler.pet-audit.cron:0 0 7 * * MON}")
    public void auditPetsWithoutRecentMedicalRecord() {
        log.info("[PetAuditScheduler] Iniciando auditoría de mascotas sin consulta reciente...");

        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        AtomicInteger alertCount = new AtomicInteger(0);

        petRepositoryPort.findByStatus(Pet.PetStatus.ACTIVE)
                .flatMap(pet ->
                        medicalRecordRepositoryPort
                                .findByPetIdAndConsultationDateAfter(pet.getId(), sixMonthsAgo)
                                .count()
                                .filter(recordCount -> recordCount == 0)
                                .map(ignored -> pet)
                )
                .doOnNext(pet -> {
                    alertCount.incrementAndGet();
                    log.warn("[ALERTA] Mascota '{}' (ID: {}) no tiene consulta médica en los últimos 6 meses.",
                            pet.getName(), pet.getId());
                })
                .doOnComplete(() -> {
                    log.info("[PetAuditScheduler] Finalizado. Mascotas sin consulta reciente: {}", alertCount.get());
                    saveLog("PetAuditScheduler", alertCount.get(), "SUCCESS",
                            "Mascotas sin consulta en 6 meses: " + alertCount.get());
                })
                .doOnError(error -> {
                    log.error("[PetAuditScheduler] Error: {}", error.getMessage());
                    saveLog("PetAuditScheduler", 0, "ERROR", error.getMessage());
                })
                .subscribe();
    }

    private void saveLog(String jobName, int records, String status, String message) {
        SchedulerLogDocument logDoc = SchedulerLogDocument.builder()
                .jobName(jobName)
                .executedAt(LocalDateTime.now())
                .recordsProcessed(records)
                .status(status)
                .message(message)
                .build();
        logRepository.save(logDoc).subscribe();
    }
}
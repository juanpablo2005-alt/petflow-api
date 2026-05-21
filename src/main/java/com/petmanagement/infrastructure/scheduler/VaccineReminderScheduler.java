package com.petmanagement.infrastructure.scheduler;

import com.petmanagement.application.port.output.VaccineRepositoryPort;
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
public class VaccineReminderScheduler {

    private final VaccineRepositoryPort vaccineRepositoryPort;
    private final SchedulerLogReactiveRepository logRepository;

    @Scheduled(cron = "${scheduler.vaccine-reminder.cron:0 0 8 * * *}")
    public void checkUpcomingVaccines() {
        log.info("[VaccineReminderScheduler] Iniciando verificación de vacunas próximas...");

        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(7);
        AtomicInteger count = new AtomicInteger(0);

        vaccineRepositoryPort.findByNextDoseDateBetween(today, limit)
                .doOnNext(vaccine -> {
                    count.incrementAndGet();
                    log.warn("[RECORDATORIO] Vacuna '{}' para mascota ID: {} vence el {}",
                            vaccine.getName(),
                            vaccine.getPetId(),
                            vaccine.getNextDoseDate());
                })
                .doOnComplete(() -> {
                    log.info("[VaccineReminderScheduler] Finalizado. Vacunas próximas encontradas: {}", count.get());
                    saveLog("VaccineReminderScheduler", count.get(), "SUCCESS",
                            "Vacunas próximas en 7 días: " + count.get());
                })
                .doOnError(error -> {
                    log.error("[VaccineReminderScheduler] Error: {}", error.getMessage());
                    saveLog("VaccineReminderScheduler", 0, "ERROR", error.getMessage());
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
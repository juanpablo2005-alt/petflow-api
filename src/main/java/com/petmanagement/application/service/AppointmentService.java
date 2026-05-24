package com.petmanagement.application.service;

import com.petmanagement.application.port.input.AppointmentUseCase;
import com.petmanagement.application.port.output.AppointmentQueuePort;
import com.petmanagement.application.port.output.AppointmentRepositoryPort;
import com.petmanagement.domain.model.Appointment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentService implements AppointmentUseCase {

    private final AppointmentQueuePort appointmentQueuePort;
    private final AppointmentRepositoryPort appointmentRepositoryPort;

    @Override
    public Mono<Long> scheduleAppointment(Appointment appointment) {
        appointment.setId(UUID.randomUUID().toString());
        appointment.setStatus(Appointment.AppointmentStatus.PENDING);
        appointment.setCreatedAt(LocalDateTime.now());

        return appointmentQueuePort.enqueue(appointment)
                .doOnSuccess(size ->
                        log.info("Cita agendada para mascota {}. Posición en cola: {}",
                                appointment.getPetId(), size)
                );
    }

    @Override
    public Mono<Appointment> processNextAppointment() {
        return appointmentQueuePort.dequeue()
                .flatMap(appointment -> {
                    appointment.setStatus(Appointment.AppointmentStatus.CONFIRMED);
                    return appointmentRepositoryPort.save(appointment)
                            .doOnSuccess(saved ->
                                    log.info("Cita confirmada y guardada en MongoDB. ID: {}", saved.getId())
                            );
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("No hay citas pendientes en la cola.");
                    return Mono.empty();
                }));
    }

    @Override
    public Mono<Long> getPendingCount() {
        return appointmentQueuePort.size();
    }
}
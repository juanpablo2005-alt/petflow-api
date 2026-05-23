package com.petmanagement.application.port.input;

import com.petmanagement.domain.model.Appointment;
import reactor.core.publisher.Mono;

public interface AppointmentUseCase {

    // Agrega la cita a la cola Redis
    Mono<Long> scheduleAppointment(Appointment appointment);

    // Procesa la siguiente cita de la cola (confirma y guarda en Mongo)
    Mono<Appointment> processNextAppointment();

    // Cuántas citas hay pendientes
    Mono<Long> getPendingCount();
}
package com.petmanagement.application.port.output;

import com.petmanagement.domain.model.Appointment;
import reactor.core.publisher.Mono;


public interface AppointmentQueuePort {

    // Publica una cita al final de la cola (RPUSH)
    Mono<Long> enqueue(Appointment appointment);

    // Extrae la primera cita de la cola (LPOP)
    Mono<Appointment> dequeue();

    // Cuántas citas hay pendientes en la cola
    Mono<Long> size();
}
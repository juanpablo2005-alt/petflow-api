package com.petmanagement.infrastructure.adapter.input.rest.controller;

import com.petmanagement.application.port.input.AppointmentUseCase;
import com.petmanagement.domain.model.Appointment;
import com.petmanagement.infrastructure.adapter.input.rest.dto.request.AppointmentRequest;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.ApiResponse;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.AppointmentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Tag(name = "Citas", description = "Gestión de citas veterinarias con cola Redis")
public class AppointmentController {

    private final AppointmentUseCase appointmentUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Agendar cita — la añade a la cola Redis",
            security = @SecurityRequirement(name = "bearerAuth"))
    public Mono<ApiResponse<String>> schedule(@Valid @RequestBody AppointmentRequest request) {
        Appointment appointment = Appointment.builder()
                .petId(request.getPetId())
                .userId(request.getUserId())
                .veterinarian(request.getVeterinarian())
                .scheduledAt(request.getScheduledAt())
                .reason(request.getReason())
                .build();

        return appointmentUseCase.scheduleAppointment(appointment)
                .map(queueSize -> ApiResponse.ok(
                        "Cita agendada. Posición en cola: " + queueSize,
                        "PENDING"
                ));
    }

    @PostMapping("/process")
    @Operation(summary = "Procesar siguiente cita de la cola — confirma y guarda en MongoDB",
            security = @SecurityRequirement(name = "bearerAuth"))
    public Mono<ApiResponse<AppointmentResponse>> processNext() {
        return appointmentUseCase.processNextAppointment()
                .map(appointment -> ApiResponse.ok("Cita confirmada", toResponse(appointment)))
                .defaultIfEmpty(ApiResponse.error("No hay citas pendientes en la cola"));
    }

    @GetMapping("/queue/status")
    @Operation(summary = "Consultar cuántas citas hay pendientes en la cola",
            security = @SecurityRequirement(name = "bearerAuth"))
    public Mono<ApiResponse<Long>> queueStatus() {
        return appointmentUseCase.getPendingCount()
                .map(count -> ApiResponse.ok("Citas pendientes en cola", count));
    }

    private AppointmentResponse toResponse(Appointment a) {
        AppointmentResponse r = new AppointmentResponse();
        r.setId(a.getId());
        r.setPetId(a.getPetId());
        r.setUserId(a.getUserId());
        r.setVeterinarian(a.getVeterinarian());
        r.setScheduledAt(a.getScheduledAt());
        r.setReason(a.getReason());
        r.setStatus(a.getStatus());
        r.setCreatedAt(a.getCreatedAt());
        return r;
    }
}
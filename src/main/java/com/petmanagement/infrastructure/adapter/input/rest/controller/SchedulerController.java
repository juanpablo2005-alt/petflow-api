package com.petmanagement.infrastructure.adapter.input.rest.controller;

import com.petmanagement.infrastructure.adapter.output.persistence.document.SchedulerLogDocument;
import com.petmanagement.infrastructure.adapter.output.persistence.repository.SchedulerLogReactiveRepository;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/scheduler")
@RequiredArgsConstructor
@Tag(name = "Scheduler", description = "Historial de ejecuciones de cron jobs")
public class SchedulerController {

    private final SchedulerLogReactiveRepository logRepository;

    @GetMapping("/logs")
    @Operation(summary = "Obtener todos los logs de cron jobs",
            security = @SecurityRequirement(name = "bearerAuth"))
    public Mono<ApiResponse<List<SchedulerLogDocument>>> getAllLogs() {
        return logRepository.findAll()
                .collectList()
                .map(logs -> ApiResponse.ok("Logs de scheduler", logs));
    }

    @GetMapping("/logs/{jobName}")
    @Operation(summary = "Obtener logs de un cron job específico",
            security = @SecurityRequirement(name = "bearerAuth"))
    public Mono<ApiResponse<List<SchedulerLogDocument>>> getLogsByJob(@PathVariable String jobName) {
        return logRepository.findByJobNameOrderByExecutedAtDesc(jobName)
                .collectList()
                .map(logs -> ApiResponse.ok("Logs de " + jobName, logs));
    }
}
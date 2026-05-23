package com.petmanagement.infrastructure.adapter.output.queue;

import com.petmanagement.application.port.output.AppointmentQueuePort;
import com.petmanagement.domain.model.Appointment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisAppointmentQueueAdapter implements AppointmentQueuePort {

    private final ReactiveRedisTemplate<String, Appointment> redisTemplate;

    @Value("${redis.queue.appointments}")
    private String queueKey;

    @Override
    public Mono<Long> enqueue(Appointment appointment) {
        return redisTemplate.opsForList()
                .rightPush(queueKey, appointment)
                .doOnSuccess(size -> log.debug("Cita añadida a la cola. Tamaño actual: {}", size))
                .doOnError(e -> log.error("Error al encolar cita: {}", e.getMessage()));
    }

    @Override
    public Mono<Appointment> dequeue() {
        return redisTemplate.opsForList()
                .leftPop(queueKey)
                .doOnSuccess(a -> {
                    if (a != null) log.debug("Cita extraída de la cola: {}", a.getId());
                })
                .doOnError(e -> log.error("Error al desencolar cita: {}", e.getMessage()));
    }

    @Override
    public Mono<Long> size() {
        return redisTemplate.opsForList()
                .size(queueKey)
                .defaultIfEmpty(0L);
    }
}
package com.petmanagement.application.port.input;

import com.petmanagement.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserUseCase {
    Mono<User> register(User user);
    Mono<User> findById(String id);
    Mono<User> update(String id, User user);
    Mono<Void> deactivate(String id);
    Mono<User> login(String email, String password);
}
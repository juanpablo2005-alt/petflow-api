package com.petmanagement.application.service;

import com.petmanagement.application.port.input.UserUseCase;
import com.petmanagement.application.port.output.UserRepositoryPort;
import com.petmanagement.domain.exception.EmailAlreadyExistsException;
import com.petmanagement.domain.exception.UserNotFoundException;
import com.petmanagement.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public Mono<User> register(User user) {
        return userRepositoryPort.existsByEmail(user.getEmail())
                .flatMap(exists -> {
                    if (exists) return Mono.error(new EmailAlreadyExistsException(user.getEmail()));
                    user.setStatus(User.UserStatus.ACTIVE);
                    user.setCreatedAt(LocalDateTime.now());
                    user.setUpdatedAt(LocalDateTime.now());
                    return userRepositoryPort.save(user);
                });
    }

    @Override
    public Mono<User> findById(String id) {
        return userRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)));
    }

    @Override
    public Mono<User> update(String id, User updatedUser) {
        return userRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                .flatMap(existing -> {
                    existing.setName(updatedUser.getName());
                    existing.setPhone(updatedUser.getPhone());
                    existing.setUpdatedAt(LocalDateTime.now());
                    return userRepositoryPort.save(existing);
                });
    }

    @Override
    public Mono<Void> deactivate(String id) {
        return userRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                .flatMap(user -> {
                    user.deactivate();
                    user.setUpdatedAt(LocalDateTime.now());
                    return userRepositoryPort.save(user);
                }).then();
    }

    @Override
    public Mono<User> login(String email, String password) {
        return userRepositoryPort.findByEmail(email)
                .switchIfEmpty(Mono.error(new UserNotFoundException(email)))
                .flatMap(user -> {
                    if (!user.getPassword().equals(password)) {
                        return Mono.error(new RuntimeException("Credenciales inválidas"));
                    }
                    return Mono.just(user);
                });
    }
}

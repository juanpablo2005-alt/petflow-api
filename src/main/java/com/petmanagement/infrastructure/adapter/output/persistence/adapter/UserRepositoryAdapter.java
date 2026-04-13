package com.petmanagement.infrastructure.adapter.output.persistence.adapter;

import com.petmanagement.application.port.output.UserRepositoryPort;
import com.petmanagement.domain.model.User;
import com.petmanagement.infrastructure.adapter.output.persistence.document.UserDocument;
import com.petmanagement.infrastructure.adapter.output.persistence.repository.UserReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserReactiveRepository repository;

    @Override
    public Mono<User> save(User user) {
        return repository.save(toDocument(user)).map(this::toDomain);
    }

    @Override
    public Mono<User> findById(String id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }

    private UserDocument toDocument(User u) {
        return UserDocument.builder()
                .id(u.getId()).name(u.getName()).email(u.getEmail())
                .password(u.getPassword()).phone(u.getPhone())
                .status(u.getStatus()).createdAt(u.getCreatedAt()).updatedAt(u.getUpdatedAt())
                .build();
    }

    private User toDomain(UserDocument d) {
        return User.builder()
                .id(d.getId()).name(d.getName()).email(d.getEmail())
                .password(d.getPassword()).phone(d.getPhone())
                .status(d.getStatus()).createdAt(d.getCreatedAt()).updatedAt(d.getUpdatedAt())
                .build();
    }
}

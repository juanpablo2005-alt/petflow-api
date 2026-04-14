package com.petmanagement.infrastructure.adapter.input.rest.controller;

import com.petmanagement.infrastructure.adapter.input.rest.dto.request.LoginRequest;
import com.petmanagement.infrastructure.config.JwtService;
import com.petmanagement.application.port.input.UserUseCase;
import com.petmanagement.domain.model.User;
import com.petmanagement.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest;
import com.petmanagement.infrastructure.adapter.input.rest.dto.request.UpdateUserRequest;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.ApiResponse;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestión de usuarios")
public class UserController {

    private final UserUseCase userUseCase;
    private final JwtService jwtService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar nuevo usuario")
    public Mono<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterUserRequest req) {
        User user = User.builder()
                .name(req.getName()).email(req.getEmail())
                .password(req.getPassword()).phone(req.getPhone())
                .build();
        return userUseCase.register(user).map(u -> ApiResponse.ok("Usuario registrado", toResponse(u)));
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y generar token")
    public Mono<ApiResponse<String>> login(@RequestBody LoginRequest request) {
        return userUseCase.login(request.getEmail(), request.getPassword())
                .map(user -> {
                    String token = jwtService.generateToken(user.getEmail());
                    return ApiResponse.ok("Login exitoso", token);
                });
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID")
    public Mono<ApiResponse<UserResponse>> findById(@PathVariable String id) {
        return userUseCase.findById(id).map(u -> ApiResponse.ok(toResponse(u)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario")
    public Mono<ApiResponse<UserResponse>> update(@PathVariable String id,
                                                   @Valid @RequestBody UpdateUserRequest req) {
        User user = User.builder().name(req.getName()).phone(req.getPhone()).build();
        return userUseCase.update(id, user).map(u -> ApiResponse.ok("Usuario actualizado", toResponse(u)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Inactivar cuenta de usuario")
    public Mono<Void> deactivate(@PathVariable String id) {
        return userUseCase.deactivate(id);
    }

    private UserResponse toResponse(User u) {
        UserResponse r = new UserResponse();
        r.setId(u.getId()); r.setName(u.getName()); r.setEmail(u.getEmail());
        r.setPhone(u.getPhone()); r.setStatus(u.getStatus());
        r.setCreatedAt(u.getCreatedAt()); r.setUpdatedAt(u.getUpdatedAt());
        return r;
    }
}

package com.petmanagement.infrastructure.adapter.input.rest.controller;

import com.petmanagement.infrastructure.adapter.input.rest.dto.request.LoginRequest;
import com.petmanagement.infrastructure.config.security.JwtService;
import com.petmanagement.application.port.input.UserUseCase;
import com.petmanagement.domain.model.User;
import com.petmanagement.infrastructure.adapter.input.rest.dto.request.RegisterUserRequest;
import com.petmanagement.infrastructure.adapter.input.rest.dto.request.UpdateUserRequest;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.ApiResponse;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.AuthResponse;
import com.petmanagement.infrastructure.adapter.input.rest.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestión de usuarios y autenticación")
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
        return userUseCase.register(user)
                .map(u -> ApiResponse.ok("Usuario registrado exitosamente", toResponse(u)));
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión — devuelve token JWT")
    public Mono<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        return userUseCase.login(request.getEmail(), request.getPassword())
                .map(user -> {
                    String token = jwtService.generateToken(user.getEmail());
                    AuthResponse authResponse = AuthResponse.builder()
                            .token(token)
                            .type("Bearer")
                            .userId(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .build();
                    return ApiResponse.ok("Login exitoso", authResponse);
                });
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", security = @SecurityRequirement(name = "bearerAuth"))
    public Mono<ApiResponse<UserResponse>> findById(@PathVariable String id) {
        return userUseCase.findById(id).map(u -> ApiResponse.ok(toResponse(u)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", security = @SecurityRequirement(name = "bearerAuth"))
    public Mono<ApiResponse<UserResponse>> update(@PathVariable String id,
                                                  @Valid @RequestBody UpdateUserRequest req) {
        User user = User.builder().name(req.getName()).phone(req.getPhone()).build();
        return userUseCase.update(id, user).map(u -> ApiResponse.ok("Usuario actualizado", toResponse(u)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Inactivar cuenta de usuario", security = @SecurityRequirement(name = "bearerAuth"))
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
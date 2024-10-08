package io.teamsgroup.caso_1_programacion_concurrente.controller.auth;

import io.teamsgroup.caso_1_programacion_concurrente.model.auth.AuthResponse;
import io.teamsgroup.caso_1_programacion_concurrente.model.auth.LoginRequest;
import io.teamsgroup.caso_1_programacion_concurrente.model.auth.RegisterRequest;
import io.teamsgroup.caso_1_programacion_concurrente.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest,
                                                 @RequestParam("rol") String rol) {
        return authService.register(registerRequest, rol);
    }
}
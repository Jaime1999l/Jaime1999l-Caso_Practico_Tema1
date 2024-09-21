package io.teamsgroup.caso_1_programacion_concurrente.model.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String mensaje;
    private String token;
    private String role;

    public AuthResponse(String mensaje, String token, String role) {
        this.mensaje = mensaje;
        this.token = token;
        this.role = role;
    }
}
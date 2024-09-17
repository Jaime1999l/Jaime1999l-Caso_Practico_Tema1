package io.teamsgroup.caso_1_programacion_concurrente.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String mensaje;
    private String token;  // Si estás utilizando JWT, aquí puedes devolver el token

    public AuthResponse(String mensaje, String token) {
        this.mensaje = mensaje;
        this.token = token;
    }
}
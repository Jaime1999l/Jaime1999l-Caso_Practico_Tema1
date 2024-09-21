package io.teamsgroup.caso_1_programacion_concurrente.model.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotNull
    private String correo;

    @NotNull
    private String contrasena;
}
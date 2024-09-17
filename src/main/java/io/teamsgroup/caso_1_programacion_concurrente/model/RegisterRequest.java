package io.teamsgroup.caso_1_programacion_concurrente.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotNull
    private String nombre;

    @NotNull
    private String apellido1;

    @NotNull
    private String apellido2;

    @NotNull
    @Size(max = 255)
    private String correo;

    @NotNull
    @Size(min = 6, max = 255)
    private String contrasena;

    @NotNull
    private Integer telefono;

    private String direccion;
}
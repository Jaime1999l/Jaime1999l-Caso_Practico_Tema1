package io.teamsgroup.caso_1_programacion_concurrente.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredencialesDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String contrasena;

    @NotNull
    private Integer usuarioId; // ID del usuario al que pertenecen las credenciales
}
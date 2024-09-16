package io.teamsgroup.caso_1_programacion_concurrente.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    @Size(max = 255)
    private String apellido1;

    @NotNull
    @Size(max = 255)
    private String apellido2;

    @NotNull
    @Size(max = 255)
    private String correo;

    @NotNull
    private Integer telefono;

    @Size(max = 255)
    private String direccion;

    @NotNull
    private Integer usuarios;

    @NotNull
    @UsuarioUsuarioUnique
    private Integer usuario;

}

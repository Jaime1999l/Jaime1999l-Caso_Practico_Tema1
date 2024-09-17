package io.teamsgroup.caso_1_programacion_concurrente.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Importante para que el ID se genere automáticamente
    @Column(nullable = false, updatable = false)
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

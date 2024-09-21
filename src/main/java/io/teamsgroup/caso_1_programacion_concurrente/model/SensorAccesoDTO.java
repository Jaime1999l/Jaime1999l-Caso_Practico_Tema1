package io.teamsgroup.caso_1_programacion_concurrente.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SensorAccesoDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    @Size(max = 255)
    private Notificacion notificacion;

    @NotNull
    @Size(max = 255)
    private String datosAcceso;

    @NotNull
    private Boolean respuesta;

    private Integer sensorAcceso;

    @NotNull
    @Size(max = 255)
    private String token;

}

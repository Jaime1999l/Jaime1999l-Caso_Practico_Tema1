package io.teamsgroup.caso_1_programacion_concurrente.model.sensor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorAccesoRequest {
    private String nombre;
    private String datosAcceso;
    private Boolean respuesta;
    private String token;

}

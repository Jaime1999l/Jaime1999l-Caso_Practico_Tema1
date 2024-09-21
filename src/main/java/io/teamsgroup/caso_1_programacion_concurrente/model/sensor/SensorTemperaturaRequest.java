package io.teamsgroup.caso_1_programacion_concurrente.model.sensor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorTemperaturaRequest {
    private String nombre;
    private String datosTemperatura;
    private String token;

}

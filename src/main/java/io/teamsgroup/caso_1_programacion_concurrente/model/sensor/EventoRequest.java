package io.teamsgroup.caso_1_programacion_concurrente.model.sensor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventoRequest {
    private String nombre;
    private String datos;
    private String token; // Token para validar la solicitud

}


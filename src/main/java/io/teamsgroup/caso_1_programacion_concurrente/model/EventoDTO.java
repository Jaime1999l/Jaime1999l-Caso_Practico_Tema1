package io.teamsgroup.caso_1_programacion_concurrente.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EventoDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @Size(max = 255)
    private String datos;

    private Integer eventosMovimiento;

    private Integer eventosTemperatura;

    private Integer eventosAcceso;

    @NotNull
    @Size(max = 255)
    private String token;

}

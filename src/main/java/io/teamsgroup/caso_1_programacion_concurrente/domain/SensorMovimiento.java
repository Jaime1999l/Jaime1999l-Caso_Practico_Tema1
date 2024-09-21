package io.teamsgroup.caso_1_programacion_concurrente.domain;

import io.teamsgroup.caso_1_programacion_concurrente.model.Notificacion;
import jakarta.persistence.*;

import java.util.concurrent.ScheduledExecutorService;
import java.util.Random;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "SensorMovimientoes")
@Getter
@Setter
public class SensorMovimiento extends Sensor {

    @Column(nullable = false)
    private String datosMovimiento;

    @OneToMany(mappedBy = "eventosMovimiento")
    private Set<Evento> sensorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensores_movimiento_id")
    private Usuario sensoresMovimiento;

    @Column(nullable = false, unique = true)
    private String token;

    private static long idCounter = 1;

    private static final Random RANDOM = new Random();

    private static ScheduledExecutorService scheduler;

    public SensorMovimiento( String nombre, Notificacion notificacion, String datosMovimiento, Set<Evento> sensorId, Usuario sensoresMovimiento) {
        super(null, nombre, notificacion);
        this.datosMovimiento = datosMovimiento;
        this.sensorId = sensorId;
        this.sensoresMovimiento = sensoresMovimiento;
    }

    public SensorMovimiento() {
        super();
    }
}

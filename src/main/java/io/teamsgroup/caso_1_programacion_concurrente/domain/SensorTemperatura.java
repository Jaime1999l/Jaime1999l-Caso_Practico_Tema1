package io.teamsgroup.caso_1_programacion_concurrente.domain;

import io.teamsgroup.caso_1_programacion_concurrente.model.Notificacion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "SensorTemperaturas")
@Getter
@Setter
public class SensorTemperatura extends Sensor {

    @Column(nullable = false)
    private Double datosTemperatura;

    @OneToMany(mappedBy = "eventosTemperatura")
    private Set<Evento> sensorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_temperatura_id")
    private Usuario sensorTemperatura;

    public SensorTemperatura(Integer id, String nombre, Notificacion notificacion, Double datosTemperatura, Set<Evento> sensorId, Usuario sensorTemperatura) {
        super(id, nombre, notificacion);
        this.datosTemperatura = datosTemperatura;
        this.sensorId = sensorId;
        this.sensorTemperatura = sensorTemperatura;
    }
    public SensorTemperatura() {
        super();
    }

}

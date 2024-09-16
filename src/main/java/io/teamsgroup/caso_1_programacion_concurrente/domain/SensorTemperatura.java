package io.teamsgroup.caso_1_programacion_concurrente.domain;

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

}

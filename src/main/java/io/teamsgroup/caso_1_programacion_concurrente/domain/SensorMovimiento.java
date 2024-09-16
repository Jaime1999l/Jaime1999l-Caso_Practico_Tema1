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

}

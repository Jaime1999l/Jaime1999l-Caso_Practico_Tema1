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
@Table(name = "SensorAccesoes")
@Getter
@Setter
public class SensorAcceso extends Sensor {

    @Column(nullable = false)
    private String datosAcceso;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean respuesta;

    @OneToMany(mappedBy = "eventosAcceso")
    private Set<Evento> sensorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_acceso_id")
    private Usuario sensorAcceso;

    @Column(nullable = false, unique = true)
    private String token;

    public SensorAcceso( String nombre, Notificacion notificacion, String datosAcceso, Boolean respuesta, Set<Evento> sensorId, Usuario sensorAcceso) {
        super(null, nombre, notificacion);
        this.datosAcceso = datosAcceso;
        this.respuesta = respuesta;
        this.sensorId = sensorId;
        this.sensorAcceso = sensorAcceso;
    }
    public SensorAcceso() {
        super();
    }
}

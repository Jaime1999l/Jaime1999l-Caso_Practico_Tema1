package io.teamsgroup.caso_1_programacion_concurrente.repos;

import io.teamsgroup.caso_1_programacion_concurrente.domain.Evento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorAcceso;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorMovimiento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorTemperatura;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventoRepository extends JpaRepository<Evento, Integer> {

    Evento findFirstByEventosMovimiento(SensorMovimiento sensorMovimiento);

    Evento findFirstByEventosTemperatura(SensorTemperatura sensorTemperatura);

    Evento findFirstByEventosAcceso(SensorAcceso sensorAcceso);

}

package io.teamsgroup.caso_1_programacion_concurrente.repos;

import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorTemperatura;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SensorTemperaturaRepository extends JpaRepository<SensorTemperatura, Integer> {

    SensorTemperatura findFirstBySensorTemperatura(Usuario usuario);

}

package io.teamsgroup.caso_1_programacion_concurrente.repos;

import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorAcceso;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SensorAccesoRepository extends JpaRepository<SensorAcceso, Integer> {

    SensorAcceso findFirstBySensorAcceso(Usuario usuario);

}

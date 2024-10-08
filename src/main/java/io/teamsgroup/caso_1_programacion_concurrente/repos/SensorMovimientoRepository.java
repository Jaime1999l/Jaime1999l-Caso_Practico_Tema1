package io.teamsgroup.caso_1_programacion_concurrente.repos;

import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorMovimiento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SensorMovimientoRepository extends JpaRepository<SensorMovimiento, Integer> {

    SensorMovimiento findFirstBySensoresMovimiento(Usuario usuario);

}

package io.teamsgroup.caso_1_programacion_concurrente.repos;

import io.teamsgroup.caso_1_programacion_concurrente.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RolRepository extends JpaRepository<Rol, Integer> {
}

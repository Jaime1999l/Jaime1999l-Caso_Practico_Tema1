package io.teamsgroup.caso_1_programacion_concurrente.repos;

import io.teamsgroup.caso_1_programacion_concurrente.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombre(String nombre);
}

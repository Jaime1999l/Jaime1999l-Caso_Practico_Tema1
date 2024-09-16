package io.teamsgroup.caso_1_programacion_concurrente.repos;

import io.teamsgroup.caso_1_programacion_concurrente.domain.Credenciales;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CredencialesRepository extends JpaRepository<Credenciales, Integer> {
}

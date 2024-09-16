package io.teamsgroup.caso_1_programacion_concurrente.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorTemperaturaDTO;
import io.teamsgroup.caso_1_programacion_concurrente.service.SensorTemperaturaService;
import io.teamsgroup.caso_1_programacion_concurrente.util.ReferencedException;
import io.teamsgroup.caso_1_programacion_concurrente.util.ReferencedWarning;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/sensorTemperaturas", produces = MediaType.APPLICATION_JSON_VALUE)
public class SensorTemperaturaResource {

    private final SensorTemperaturaService sensorTemperaturaService;

    public SensorTemperaturaResource(final SensorTemperaturaService sensorTemperaturaService) {
        this.sensorTemperaturaService = sensorTemperaturaService;
    }

    @GetMapping
    public ResponseEntity<List<SensorTemperaturaDTO>> getAllSensorTemperaturas() {
        return ResponseEntity.ok(sensorTemperaturaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorTemperaturaDTO> getSensorTemperatura(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(sensorTemperaturaService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSensorTemperatura(
            @RequestBody @Valid final SensorTemperaturaDTO sensorTemperaturaDTO) {
        final Integer createdId = sensorTemperaturaService.create(sensorTemperaturaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateSensorTemperatura(
            @PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final SensorTemperaturaDTO sensorTemperaturaDTO) {
        sensorTemperaturaService.update(id, sensorTemperaturaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSensorTemperatura(
            @PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = sensorTemperaturaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        sensorTemperaturaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

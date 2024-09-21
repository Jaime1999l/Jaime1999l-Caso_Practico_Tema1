package io.teamsgroup.caso_1_programacion_concurrente.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorAccesoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.SensorAccesoService;
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
@RequestMapping(value = "/api/sensorAccesos", produces = MediaType.APPLICATION_JSON_VALUE)
public class SensorAccesoResource {

    private final SensorAccesoService sensorAccesoService;

    public SensorAccesoResource(final SensorAccesoService sensorAccesoService) {
        this.sensorAccesoService = sensorAccesoService;
    }

    @GetMapping
    public ResponseEntity<List<SensorAccesoDTO>> getAllSensorAccesos() {
        return ResponseEntity.ok(sensorAccesoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorAccesoDTO> getSensorAcceso(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(sensorAccesoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSensorAcceso(
            @RequestBody @Valid final SensorAccesoDTO sensorAccesoDTO) {
        final Integer createdId = sensorAccesoService.create(sensorAccesoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateSensorAcceso(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final SensorAccesoDTO sensorAccesoDTO) {
        sensorAccesoService.update(id, sensorAccesoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSensorAcceso(@PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = sensorAccesoService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        sensorAccesoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

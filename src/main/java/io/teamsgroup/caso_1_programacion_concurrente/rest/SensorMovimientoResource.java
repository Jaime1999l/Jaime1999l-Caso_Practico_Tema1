package io.teamsgroup.caso_1_programacion_concurrente.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorMovimientoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.SensorMovimientoService;
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
@RequestMapping(value = "/api/sensorMovimientos", produces = MediaType.APPLICATION_JSON_VALUE)
public class SensorMovimientoResource {

    private final SensorMovimientoService sensorMovimientoService;

    public SensorMovimientoResource(final SensorMovimientoService sensorMovimientoService) {
        this.sensorMovimientoService = sensorMovimientoService;
    }

    @GetMapping
    public ResponseEntity<List<SensorMovimientoDTO>> getAllSensorMovimientos() {
        return ResponseEntity.ok(sensorMovimientoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorMovimientoDTO> getSensorMovimiento(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(sensorMovimientoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSensorMovimiento(
            @RequestBody @Valid final SensorMovimientoDTO sensorMovimientoDTO) {
        final Integer createdId = sensorMovimientoService.create(sensorMovimientoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateSensorMovimiento(
            @PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final SensorMovimientoDTO sensorMovimientoDTO) {
        sensorMovimientoService.update(id, sensorMovimientoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSensorMovimiento(
            @PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = sensorMovimientoService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        sensorMovimientoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

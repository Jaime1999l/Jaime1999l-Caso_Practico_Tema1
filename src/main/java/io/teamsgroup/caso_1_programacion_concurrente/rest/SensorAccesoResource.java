package io.teamsgroup.caso_1_programacion_concurrente.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorAccesoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.model.sensor.SensorAccesoRequest;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.SensorAccesoService;
import io.teamsgroup.caso_1_programacion_concurrente.util.ReferencedException;
import io.teamsgroup.caso_1_programacion_concurrente.util.ReferencedWarning;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/sensorAccesos", produces = MediaType.APPLICATION_JSON_VALUE)
public class SensorAccesoResource {

    private final SensorAccesoService sensorAccesoService;

    public SensorAccesoResource(final SensorAccesoService sensorAccesoService) {
        this.sensorAccesoService = sensorAccesoService;
    }

    @PostMapping("/sensoresAcceso")
    @ApiResponse(responseCode = "200", description = "Get all access sensors")
    public ResponseEntity<List<SensorAccesoDTO>> getAllSensoresAcceso(@RequestBody SensorAccesoRequest request) {
        // Pasar el token proporcionado en el cuerpo de la solicitud al método findAll
        return ResponseEntity.ok(sensorAccesoService.findAll(request.getToken()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorAccesoDTO> getSensorAcceso(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(sensorAccesoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSensorAcceso(@RequestBody @Valid final SensorAccesoDTO sensorAccesoDTO) {
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



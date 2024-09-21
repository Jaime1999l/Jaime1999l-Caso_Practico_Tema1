package io.teamsgroup.caso_1_programacion_concurrente.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorTemperaturaDTO;
import io.teamsgroup.caso_1_programacion_concurrente.model.sensor.SensorTemperaturaRequest;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.SensorTemperaturaService;
import io.teamsgroup.caso_1_programacion_concurrente.util.ReferencedException;
import io.teamsgroup.caso_1_programacion_concurrente.util.ReferencedWarning;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/sensorTemperaturas")
public class SensorTemperaturaResource {

    private final SensorTemperaturaService sensorTemperaturaService;

    public SensorTemperaturaResource(final SensorTemperaturaService sensorTemperaturaService) {
        this.sensorTemperaturaService = sensorTemperaturaService;
    }

    @PostMapping("/sensoresTemperatura")
    @ApiResponse(responseCode = "200", description = "Get all temperature sensors")
    public ResponseEntity<List<SensorTemperaturaDTO>> getAllSensoresTemperatura(@RequestBody SensorTemperaturaRequest request) {
        // Pasar el token proporcionado en el cuerpo de la solicitud al método findAll
        return ResponseEntity.ok(sensorTemperaturaService.findAll(request.getToken()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorTemperaturaDTO> getSensorTemperatura(@PathVariable final Integer id,
                                                                     @RequestHeader("Authorization") String token) {
        SensorTemperaturaDTO sensor = sensorTemperaturaService.get(id);
        return ResponseEntity.ok(sensor);
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSensorTemperatura(@RequestBody final SensorTemperaturaDTO sensorTemperaturaDTO,
                                                           @RequestHeader("Authorization") String token) {
        Integer createdId = sensorTemperaturaService.create(sensorTemperaturaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSensorTemperatura(@PathVariable final Integer id,
                                                        @RequestBody final SensorTemperaturaDTO sensorTemperaturaDTO,
                                                        @RequestHeader("Authorization") String token) {
        sensorTemperaturaService.update(id, sensorTemperaturaDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSensorTemperatura(@PathVariable final Integer id,
                                                        @RequestHeader("Authorization") String token) {
        final ReferencedWarning referencedWarning = sensorTemperaturaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        sensorTemperaturaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


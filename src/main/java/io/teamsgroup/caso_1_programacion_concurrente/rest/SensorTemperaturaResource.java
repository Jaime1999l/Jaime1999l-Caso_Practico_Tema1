package io.teamsgroup.caso_1_programacion_concurrente.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorTemperaturaDTO;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.SensorTemperaturaService;
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

    @GetMapping
    public ResponseEntity<List<SensorTemperaturaDTO>> getAllSensorTemperaturas(@RequestHeader("Authorization") String token) {
        List<SensorTemperaturaDTO> sensores = sensorTemperaturaService.findAll();
        return ResponseEntity.ok(sensores);
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
        sensorTemperaturaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


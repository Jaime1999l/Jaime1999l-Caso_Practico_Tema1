package io.teamsgroup.caso_1_programacion_concurrente.controller.sensor;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorAccesoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorMovimientoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorTemperaturaDTO;
import io.teamsgroup.caso_1_programacion_concurrente.model.sensor.SensorAccesoRequest;
import io.teamsgroup.caso_1_programacion_concurrente.model.sensor.SensorMovimientoRequest;
import io.teamsgroup.caso_1_programacion_concurrente.model.sensor.SensorTemperaturaRequest;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.SensorAccesoService;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.SensorMovimientoService;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.SensorTemperaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    private final SensorAccesoService sensorAccesoService;
    private final SensorMovimientoService sensorMovimientoService;
    private final SensorTemperaturaService sensorTemperaturaService;

    public SensorController(final SensorAccesoService sensorAccesoService, final SensorMovimientoService sensorMovimientoService, final SensorTemperaturaService sensorTemperaturaService) {
        this.sensorAccesoService = sensorAccesoService;
        this.sensorMovimientoService = sensorMovimientoService;
        this.sensorTemperaturaService = sensorTemperaturaService;
    }

    @PostMapping("/sensoresTemperatura")
    @ApiResponse(responseCode = "200", description = "Get all sensors")
    public ResponseEntity<List<SensorTemperaturaDTO>> getAllSensoresTemperatura(@RequestBody SensorTemperaturaRequest request) {
        return ResponseEntity.ok(sensorTemperaturaService.findAll());
    }

    @PostMapping("/sensoresMovimiento")
    @ApiResponse(responseCode = "200", description = "Get all sensors")
    public ResponseEntity<List<SensorMovimientoDTO>> getAllSensoresMovimiento(@RequestBody SensorMovimientoRequest request) {
        return ResponseEntity.ok(sensorMovimientoService.findAll());
    }

    @PostMapping("/sensoresAcceso")
    @ApiResponse(responseCode = "200", description = "Get all sensors")
    public ResponseEntity<List<SensorAccesoDTO>> getAllSensoresAcceso(@RequestBody SensorAccesoRequest request) {
        return ResponseEntity.ok(sensorAccesoService.findAll());
    }
}


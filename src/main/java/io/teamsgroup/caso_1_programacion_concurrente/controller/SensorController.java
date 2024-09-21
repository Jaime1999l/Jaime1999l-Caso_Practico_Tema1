package io.teamsgroup.caso_1_programacion_concurrente.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorAccesoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorMovimientoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorTemperaturaDTO;
import io.teamsgroup.caso_1_programacion_concurrente.service.SensorAccesoService;
import io.teamsgroup.caso_1_programacion_concurrente.service.SensorMovimientoService;
import io.teamsgroup.caso_1_programacion_concurrente.service.SensorTemperaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<List<SensorTemperaturaDTO>> getAllSensoresTemperatura() {
        return ResponseEntity.ok(sensorTemperaturaService.findAll());
    }

    @PostMapping("/sensoresMovimiento")
    @ApiResponse(responseCode = "200", description = "Get all sensors")
    public ResponseEntity<List<SensorMovimientoDTO>> getAllSensoresMovimiento() {
        return ResponseEntity.ok(sensorMovimientoService.findAll());
    }

    @PostMapping("/sensoresAcceso")
    @ApiResponse(responseCode = "200", description = "Get all sensors")
    public ResponseEntity<List<SensorAccesoDTO>> getAllSensoresAcceso() {
        return ResponseEntity.ok(sensorAccesoService.findAll());
    }
}

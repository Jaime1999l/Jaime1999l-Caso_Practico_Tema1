package io.teamsgroup.caso_1_programacion_concurrente.controller.sensor;

import io.teamsgroup.caso_1_programacion_concurrente.model.SensorAccesoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorMovimientoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorTemperaturaDTO;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.SensorAccesoService;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.SensorMovimientoService;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.SensorTemperaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    private final SensorAccesoService sensorAccesoService;
    private final SensorMovimientoService sensorMovimientoService;
    private final SensorTemperaturaService sensorTemperaturaService;

    public SensorController(final SensorAccesoService sensorAccesoService,
                            final SensorMovimientoService sensorMovimientoService,
                            final SensorTemperaturaService sensorTemperaturaService) {
        this.sensorAccesoService = sensorAccesoService;
        this.sensorMovimientoService = sensorMovimientoService;
        this.sensorTemperaturaService = sensorTemperaturaService;
    }

    // Cambio de @PostMapping a @GetMapping y uso de @RequestParam para recibir el token
    @GetMapping("/sensoresTemperatura")
    public ResponseEntity<List<SensorTemperaturaDTO>> getAllSensoresTemperatura(@RequestParam String token) {
        return ResponseEntity.ok(sensorTemperaturaService.findAll(token));
    }

    @GetMapping("/sensoresMovimiento")
    public ResponseEntity<List<SensorMovimientoDTO>> getAllSensoresMovimiento(@RequestParam String token) {
        return ResponseEntity.ok(sensorMovimientoService.findAll(token));
    }

    @GetMapping("/sensoresAcceso")
    public ResponseEntity<List<SensorAccesoDTO>> getAllSensoresAcceso(@RequestParam String token) {
        return ResponseEntity.ok(sensorAccesoService.findAll(token));
    }
}

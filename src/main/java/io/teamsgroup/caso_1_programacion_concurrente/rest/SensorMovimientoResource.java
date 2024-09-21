package io.teamsgroup.caso_1_programacion_concurrente.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorMovimientoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.model.sensor.SensorMovimientoRequest;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.SensorMovimientoService;
import io.teamsgroup.caso_1_programacion_concurrente.util.ReferencedException;
import io.teamsgroup.caso_1_programacion_concurrente.util.ReferencedWarning;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/sensorMovimientos")
public class SensorMovimientoResource {

    private final SensorMovimientoService sensorMovimientoService;

    public SensorMovimientoResource(final SensorMovimientoService sensorMovimientoService) {
        this.sensorMovimientoService = sensorMovimientoService;
    }

    @PostMapping("/sensoresMovimiento")
    @ApiResponse(responseCode = "200", description = "Get all movement sensors")
    public ResponseEntity<List<SensorMovimientoDTO>> getAllSensoresMovimiento(@RequestBody SensorMovimientoRequest request) {
        // Pasar el token proporcionado en el cuerpo de la solicitud al método findAll
        return ResponseEntity.ok(sensorMovimientoService.findAll(request.getToken()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorMovimientoDTO> getSensorMovimiento(@PathVariable final Integer id,
                                                                   @RequestHeader("Authorization") String token) {
        SensorMovimientoDTO sensor = sensorMovimientoService.get(id);
        return ResponseEntity.ok(sensor);
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSensorMovimiento(@RequestBody final SensorMovimientoDTO sensorMovimientoDTO,
                                                          @RequestHeader("Authorization") String token) {
        Integer createdId = sensorMovimientoService.create(sensorMovimientoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSensorMovimiento(@PathVariable final Integer id,
                                                       @RequestBody final SensorMovimientoDTO sensorMovimientoDTO,
                                                       @RequestHeader("Authorization") String token) {
        sensorMovimientoService.update(id, sensorMovimientoDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSensorMovimiento(@PathVariable final Integer id,
                                                       @RequestHeader("Authorization") String token) {
        final ReferencedWarning referencedWarning = sensorMovimientoService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        sensorMovimientoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

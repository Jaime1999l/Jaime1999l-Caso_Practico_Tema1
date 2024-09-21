package io.teamsgroup.caso_1_programacion_concurrente.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.teamsgroup.caso_1_programacion_concurrente.model.SensorMovimientoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.SensorMovimientoService;
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

    @GetMapping
    public ResponseEntity<List<SensorMovimientoDTO>> getAllSensorMovimientos(@RequestHeader("Authorization") String token) {
        List<SensorMovimientoDTO> sensores = sensorMovimientoService.findAll();
        return ResponseEntity.ok(sensores);
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
        sensorMovimientoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

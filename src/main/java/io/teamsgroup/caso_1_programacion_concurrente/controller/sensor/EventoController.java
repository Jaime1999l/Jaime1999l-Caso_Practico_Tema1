package io.teamsgroup.caso_1_programacion_concurrente.controller.sensor;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.teamsgroup.caso_1_programacion_concurrente.model.EventoDTO;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.EventoService;
import io.teamsgroup.caso_1_programacion_concurrente.model.sensor.EventoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping("/events")
    @ApiResponse(responseCode = "200", description = "Get all events")
    public ResponseEntity<List<EventoDTO>> getAllEventos(@RequestBody EventoRequest request) {
        return ResponseEntity.ok(eventoService.findAll());
    }
}

